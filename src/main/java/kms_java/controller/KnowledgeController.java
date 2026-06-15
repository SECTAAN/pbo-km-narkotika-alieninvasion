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
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
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
        guiView.getBtnHapus().setOnAction(e -> hapusData());
        guiView.getBtnClear().setOnAction(e -> bersihkanTabel());
        guiView.getBtnCari().setOnAction(e -> cariData());
        guiView.getBtnExport().setOnAction(e -> exportKeTxt());
    }

    private void muatDatasetPdf() {
        TextInputDialog dialog = new TextInputDialog("D:/DatasetKMS");
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

    private void hapusData() {
        Putusan terpilih = guiView.getTable().getSelectionModel().getSelectedItem();
        if (terpilih != null) {
            boolean sukses = repository.hapus(terpilih.getNomorPutusan());
            if (sukses) {
                perbaruiTabel();
                tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil dihapus!");
            }
        } else {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Klik/pilih baris data di tabel terlebih dahulu untuk dihapus!");
        }
    }

    private void bersihkanTabel() {
        repository = new KnowledgeRepository();
        perbaruiTabel();
        tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Data tabel telah dibersihkan.");
    }

    private void cariData() {
        String keyword = guiView.getTxtCari().getText();
        if (keyword != null && !keyword.trim().isEmpty()) {
            ArrayList<Putusan> hasil = repository.cari(keyword);

            Collections.sort(hasil);

            ObservableList<Putusan> dataTabel = FXCollections.observableArrayList(hasil);
            guiView.getTable().setItems(dataTabel);
            guiView.getLblStatus().setText("Hasil pencarian: " + hasil.size() + " data ditemukan");
        } else {
            perbaruiTabel();
        }
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Data masih kosong. Muat PDF terlebih dahulu.");
            return;
        }

        double rataRata = statistik.hitungRataRataBerat(daftar);
        String jenisTerbanyak = statistik.getJenisTerbanyak(daftar);

        String pesan = "Total Data Putusan: " + daftar.size() + " Kasus\n"
                + "Rata-rata berat barang bukti: " + rataRata + " gram\n"
                + "Jenis Narkotika Terbanyak: " + jenisTerbanyak;

        tampilkanAlert(Alert.AlertType.INFORMATION, "Analisis Statistik", pesan);
    }

    private void exportKeTxt() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Tidak ada data untuk diekspor!");
            return;
        }

        try (FileWriter writer = new FileWriter("Laporan_Statistik_KMS.txt")) {
            writer.write("=== LAPORAN STATISTIK PUTUSAN PENGADILAN NARKOTIKA ===\n\n");
            writer.write("Total Data Diekstrak  : " + daftar.size() + " Kasus\n");
            writer.write("Rata-rata Berat Bukti : " + statistik.hitungRataRataBerat(daftar) + " gram\n");
            writer.write("Jenis Kasus Terbanyak : " + statistik.getJenisTerbanyak(daftar) + "\n\n");
            writer.write("=== DAFTAR PUTUSAN (Diurutkan Berdasarkan Berat Bukti Terbesar) ===\n");

            Collections.sort(daftar);
            for (Putusan p : daftar) {
                writer.write("- No: " + p.getNomorPutusan() + " | Terdakwa: " + p.getNamaTerdakwa() + " | Bukti: " + p.getBeratBarangBukti() + "g\n");
            }

            tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diekspor ke Laporan_Statistik_KMS.txt (Poin Bonus +2!)");
        } catch (Exception e) {
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Gagal mengekspor file: " + e.getMessage());
        }
    }

    private void perbaruiTabel() {
        ArrayList<Putusan> daftar = repository.getSemuaData();

        Collections.sort(daftar);

        ObservableList<Putusan> dataTabel = FXCollections.observableArrayList(daftar);
        guiView.getTable().setItems(dataTabel);

        guiView.getLblStatus().setText("Total Data Tersimpan: " + daftar.size() + " | Memori Siap");
    }

    private void tampilkanAlert(Alert.AlertType tipe, String judul, String pesan) {
        Alert alert = new Alert(tipe);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}