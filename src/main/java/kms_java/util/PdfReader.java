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
        String terdakwa = ekstrakDenganRegex(teksMentah, "Terdakwa\\s+([A-Za-z\\s]+)\\s+");
        String jenis = ekstrakDenganRegex(teksMentah, "(Sabu|Ganja|Ekstasi|Narkotika Golongan\\s+[IVX]+)");

        double berat = 0.0;
        String teksBerat = ekstrakDenganRegex(teksMentah, "([0-9]+[\\.,]?[0-9]*)\\s*(gram|gr)");
        if (!teksBerat.equals("Tidak Ditemukan")) {
            try {
                berat = Double.parseDouble(teksBerat.replace(",", "."));
            } catch (NumberFormatException ignored) {}
        }

        String vonisTeks = ekstrakDenganRegex(teksMentah, "pidana.*?selama\\s+([\\w\\s]+)\\s*");
        int vonisBulan = 0;
        if (vonisTeks.contains("tahun")) {
            vonisBulan = 60;
        } else if (vonisTeks.contains("bulan")) {
            vonisBulan = 12; 
        }

        return new Putusan(
                nomor, "PN Default", "Belum Diketahui", terdakwa, 0,
                jenis, berat, "Pasal 114 / 112", "Terdakwa",
                vonisBulan, 0.0, "Belum Diketahui"
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