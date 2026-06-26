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

        String terdakwa = ekstrakDenganRegex(teksMentah, "Nama\\s+lengkap\\s*:\\s*([^\\n\\r]+)");

        terdakwa = terdakwa.replaceAll(";", "").trim();

        if (terdakwa.equals("Tidak Ditemukan") || terdakwa.toLowerCase().contains("ditahan")) {
            terdakwa = "Terdakwa (Nama Tidak Terbaca Sempurna)";
        }

        String jenis = ekstrakDenganRegex(teksMentah, "(Sabu|Ganja|Ekstasi|Narkotika Golongan\\s+[IVX]+)");

        double berat = 0.0;
        String teksBerat = ekstrakDenganRegex(teksMentah, "([0-9]+[\\.,]?[0-9]*)\\s*(gram|gr)");
        if (!teksBerat.equals("Tidak Ditemukan")) {
            try {
                berat = Double.parseDouble(teksBerat.replace(",", "."));
            } catch (NumberFormatException ignored) {}
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


        return new Putusan(
                nomor, "PN Surabaya", "2024", terdakwa, 30,
                jenis, berat, "Pasal 112 / 114 UU Narkotika", "Bandar / Kurir",
                vonisBulan, vonisDenda, "Majelis Hakim PN"
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