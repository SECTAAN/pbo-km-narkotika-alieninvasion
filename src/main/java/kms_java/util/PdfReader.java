package kms_java.util;

import kms_java.model.Putusan;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PdfReader {
    public Putusan prosesPdfKeObjek(String lokasiFile) {
        String teksMentah = "";
        try (PDDocument dokumen = PDDocument.load(new File(lokasiFile))) {
            PDFTextStripper penyedot = new PDFTextStripper();
            teksMentah = penyedot.getText(dokumen);
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca PDF: " + lokasiFile);
        }

        return new Putusan("Belum Diambil", "Belum Diambil", "Belum Diambil", 0.0, "Belum Diambil", "Belum Diambil");
    }
}