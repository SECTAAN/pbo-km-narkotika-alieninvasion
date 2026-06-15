package kms_java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import kms_java.model.KnowledgeRepository;
import kms_java.model.Putusan;
import kms_java.model.StatistikPutusan;
import kms_java.util.PdfReader;
import kms_java.view.JavaFXView;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class KnowledgeController {
    private KnowledgeRepository repository;
    private StatistikPutusan statistik;
    private PdfReader pdfReader;
    private JavaFXView guiView;

    public KnowledgeController(JavaFXView guiView) {
        this.repository = new KnowledgeRepository();
        this.statistik = new StatistikPutusan();
        this.pdfReader = new PdfReader();
        this.guiView = guiView;

        inisialisasiEvent();
    }

    private void inisialisasiEvent() {
        guiView.getBtnLoadPdf().setOnAction(e -> muatDatasetPdf());

        guiView.getBtnStatistik().setOnAction(e -> tampilkanStatistik());
    }

    private void muatDatasetPdf() {
        TextInputDialog dialog = new TextInputDialog("C:/Tugas/PDF");
        dialog.setTitle("Muat Dataset PDF");
        dialog.setHeaderText("Masukkan lokasi folder tempat file PDF berada");
        dialog.setContentText("Path Folder:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String pathFolder = result.get();
            File folder = new File(pathFolder);

            if (folder.exists() && folder.isDirectory()) {
                File[] daftarFile = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

                if (daftarFile != null && daftarFile.length > 0) {
                    int sukses = 0;
                    for (File file : daftarFile) {
                        Putusan putusanBaru = pdfReader.prosesPdfKeObjek(file.getAbsolutePath());
                        if (putusanBaru != null) {
                            repository.tambahData(putusanBaru);
                            sukses++;
                        }
                    }
                    perbaruiTabel();
                    tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Berhasil memuat " + sukses + " data putusan!");
                } else {
                    tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Tidak ada file PDF di folder tersebut.");
                }
            } else {
                tampilkanAlert(Alert.AlertType.ERROR, "Error", "Folder tidak ditemukan!");
            }
        }
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Data masih kosong. Muat PDF terlebih dahulu.");
            return;
        }

        double rataRata = statistik.hitungRataRataBerat(daftar);
        int kasusSabu = statistik.hitungTotalPerJenis(daftar, "Sabu");
        int kasusGanja = statistik.hitungTotalPerJenis(daftar, "Ganja");

        String pesan = "Rata-rata berat barang bukti: " + rataRata + " gram\n"
                + "Total kasus Sabu-sabu: " + kasusSabu + " kasus\n"
                + "Total kasus Ganja: " + kasusGanja + " kasus";

        tampilkanAlert(Alert.AlertType.INFORMATION, "Analisis Statistik", pesan);
    }

    private void perbaruiTabel() {
        ObservableList<Putusan> dataTabel = FXCollections.observableArrayList(repository.getSemuaData());
        guiView.getTable().setItems(dataTabel);
    }

    private void tampilkanAlert(Alert.AlertType tipe, String judul, String pesan) {
        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}