package kms_java.util;

import kms_java.model.Putusan;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReader {
    public Putusan prosesPdfKeObjek(String lokasiFile) {
        String teksMentah = "";
        try (PDDocument dokumen = PDDocument.load(new File(lokasiFile))) {
            PDFTextStripper penyedot = new PDFTextStripper();
            teksMentah = penyedot.getText(dokumen);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca PDF: " + lokasiFile);
            return null;
        }

        String nomor = ekstrakDenganRegex(teksMentah, "Nomor\\s+([\\w\\/\\.-]+)\\s*");

        String pengadilan = ekstrakDenganRegex(teksMentah, "(Pengadilan\\s+Negeri\\s+[A-Za-z]+)");
        if (pengadilan.equals("Tidak Ditemukan")) pengadilan = "PN Surabaya";

        String tahun = ekstrakDenganRegex(teksMentah, "Tahun\\s+(202[0-9])");
        if (tahun.equals("Tidak Ditemukan")) tahun = "2024";

        String terdakwa = ekstrakDenganRegex(teksMentah, "Nama\\s+lengkap\\s*:\\s*([^\\n\\r]+)");
        terdakwa = terdakwa.replaceAll(";", "").trim();
        if (terdakwa.equals("Tidak Ditemukan") || terdakwa.toLowerCase().contains("ditahan")) {
            terdakwa = "Terdakwa (Nama Tidak Terbaca Sempurna)";
        }

        int umur = 30;
        String teksUmur = ekstrakDenganRegex(teksMentah, "Umur.*?([0-9]{2})\\s*tahun");
        if (!teksUmur.equals("Tidak Ditemukan")) {
            try { umur = Integer.parseInt(teksUmur); } catch (Exception ignored) {}
        }

        String jenis = ekstrakDenganRegex(teksMentah, "(Sabu|Ganja|Ekstasi|Narkotika Golongan\\s+[IVX]+)");

        double berat = 0.0;
        String teksBerat = ekstrakDenganRegex(teksMentah, "([0-9]+[\\.,]?[0-9]*)\\s*(gram|gr)");
        if (!teksBerat.equals("Tidak Ditemukan")) {
            try { berat = Double.parseDouble(teksBerat.replace(",", ".")); } catch (NumberFormatException ignored) {}
        }

        String pasal = "Pasal 112 UU Narkotika";
        String peran = "Kurir / Perantara";

        if (teksMentah.contains("Pasal 127") || teksMentah.contains("pasal 127")) {
            pasal = "Pasal 127 UU Narkotika";
            peran = "Pengguna / Pecandu";
        } else if (teksMentah.contains("Pasal 114") || teksMentah.contains("pasal 114")) {
            pasal = "Pasal 114 UU Narkotika";
            peran = "Bandar Narkotika";
        }

        int vonisBulan = 0;
        String teksVonis = ekstrakDenganRegex(teksMentah, "selama\\s+([a-zA-Z0-9\\s\\(\\)]+)(tahun|bulan)");
        if (!teksVonis.equals("Tidak Ditemukan")) {
            Matcher mAngka = Pattern.compile("([0-9]+)").matcher(teksVonis);
            if (mAngka.find()) {
                vonisBulan = Integer.parseInt(mAngka.group(1));
                if (teksVonis.toLowerCase().contains("tahun")) vonisBulan *= 12;
            } else {
                vonisBulan = teksVonis.toLowerCase().contains("tahun") ? 60 : 12;
            }
        }

        double vonisDenda = 800000000.0;
        String teksDenda = ekstrakDenganRegex(teksMentah, "denda.*?Rp\\.?\\s*([0-9\\.]+)");
        if (!teksDenda.equals("Tidak Ditemukan")) {
            try {
                vonisDenda = Double.parseDouble(teksDenda.replace(".", ""));
            } catch (NumberFormatException ignored) {}
        }

        String hakim = "Majelis Hakim PN";
        String teksHakim = ekstrakDenganRegex(teksMentah, "oleh\\s+kami\\s*,?\\s*([A-Z][A-Za-z\\s\\.\\,']+?)\\s*(?:S\\.H\\.|M\\.H\\.|sebagai)");

        if (teksHakim.equals("Tidak Ditemukan")) {
            teksHakim = ekstrakDenganRegex(teksMentah, "Hakim\\s+Ketua(?:\\s+Majelis)?[\\s\\n:]+([A-Z][A-Za-z\\s\\.\\,']{4,40}?)(?:S\\.H\\.|M\\.H\\.|\\n)");
        }

        if (!teksHakim.equals("Tidak Ditemukan") && teksHakim.length() > 3) {
            hakim = teksHakim.replaceAll(";", "").trim() + ", S.H.";
        }

        return new Putusan(
                nomor, pengadilan, tahun, terdakwa, umur,
                jenis, berat, pasal, peran,
                vonisBulan, vonisDenda, hakim
        );
    }

    private String ekstrakDenganRegex(String teksLengkap, String polaRegex) {
        Pattern pattern = Pattern.compile(polaRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(teksLengkap);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Tidak Ditemukan";
    }
}