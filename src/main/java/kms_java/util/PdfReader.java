package kms_java.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PdfReader {

    public String bacaTeksDariPdf(String lokasiFile) {
        String teksHasil = "";
        PDDocument dokumen = null;

        try {
            File filePdf = new File(lokasiFile);
            dokumen = PDDocument.load(filePdf);

            PDFTextStripper penyedotTeks = new PDFTextStripper();
            teksHasil = penyedotTeks.getText(dokumen);

        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca file PDF di: " + lokasiFile);
            System.out.println("Pesan Error: " + e.getMessage());
        } finally {
            try {
                if (dokumen != null) {
                    dokumen.close();
                }
            } catch (IOException ex) {
                System.out.println("[ERROR] Gagal menutup file PDF.");
            }
        }

        return teksHasil;
    }
}