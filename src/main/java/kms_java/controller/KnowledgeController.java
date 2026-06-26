package kms_java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import kms_java.model.KnowledgeRepository;
import kms_java.model.Putusan;
import kms_java.model.StatistikPutusan;
import kms_java.util.InputHandler;
import kms_java.util.PdfReader;
import kms_java.view.JavaFXView;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class KnowledgeController {
    private KnowledgeRepository repository;
    private PdfReader pdfReader;
    private JavaFXView guiView;

    public KnowledgeController(JavaFXView guiView) {
        this.repository = new KnowledgeRepository();
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
        String pathFolder = "pdf-putusan";
        File folder = new File(pathFolder);

        if (folder.exists() && folder.isDirectory()) {
            File[] daftarFile = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (daftarFile != null && daftarFile.length > 0) {
                int sukses = 0;
                for (File file : daftarFile) {
                    Putusan putusanBaru = pdfReader.prosesPdfKeObjek(file.getAbsolutePath());
                    if (putusanBaru != null) {
                        repository.simpan(putusanBaru);
                        sukses++;
                    }
                }
                perbaruiTabel();
                tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Berhasil memuat " + sukses + " data putusan secara otomatis dari folder project!");
            } else {
                tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Folder 'pdf-putusan' kosong. Harap masukkan file PDF ke dalamnya.");
            }
        } else {
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Folder 'pdf-putusan' tidak ditemukan!\nPastikan Anda sudah membuat folder 'pdf-putusan' sejajar dengan folder 'src'.");
        }
    }

    private void hapusData() {
        Putusan terpilih = guiView.getTable().getSelectionModel().getSelectedItem();
        if (terpilih != null) {
            boolean sukses = repository.hapus(terpilih.getNomorPerkara());
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
            ArrayList<Putusan> hasilCariNama = repository.cariByNama(keyword);
            Putusan hasilCariNomor = repository.cariByNomor(keyword);

            if (hasilCariNomor != null && !hasilCariNama.contains(hasilCariNomor)) {
                hasilCariNama.add(hasilCariNomor);
            }

            Collections.sort(hasilCariNama);
            ObservableList<Putusan> dataTabel = FXCollections.observableArrayList(hasilCariNama);
            guiView.getTable().setItems(dataTabel);
            guiView.getLblStatus().setText("Hasil pencarian: " + hasilCariNama.size() + " data ditemukan");
        } else {
            perbaruiTabel();
        }
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
        if (daftar.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Data masih kosong. Muat PDF terlebih dahulu.");
            return;
        }

        StatistikPutusan stat = new StatistikPutusan(daftar);
        stat.hitungSemua();

        StringBuilder pesan = new StringBuilder();
        pesan.append("Total Data Putusan: ").append(stat.getTotalPutusan()).append(" Kasus\n");
        pesan.append("Rata-rata Vonis Hukuman: ").append(stat.getRataRataVonis()).append(" Bulan\n");
        pesan.append("Jenis Narkotika Terbanyak: ").append(stat.getJenisNarkotikaTerbanyak()).append("\n\n");
        pesan.append("--- Distribusi Peran (Menggunakan Array) ---\n");

        for (String peran : stat.getDistribusiPeran()) {
            pesan.append("- ").append(peran).append("\n");
        }

        tampilkanAlert(Alert.AlertType.INFORMATION, "Analisis Statistik", pesan.toString());
    }

    private void exportKeTxt() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
        if (daftar.isEmpty()) {
            tampilkanAlert(Alert.AlertType.WARNING, "Peringatan", "Tidak ada data untuk diekspor!");
            return;
        }

        StatistikPutusan stat = new StatistikPutusan(daftar);
        stat.hitungSemua();

        try (FileWriter writer = new FileWriter("Laporan_Statistik_KMS.txt")) {
            writer.write("=== LAPORAN STATISTIK PUTUSAN PENGADILAN NARKOTIKA ===\n\n");
            writer.write("Total Data Diekstrak  : " + stat.getTotalPutusan() + " Kasus\n");
            writer.write("Rata-rata Vonis       : " + stat.getRataRataVonis() + " Bulan\n");
            writer.write("Jenis Kasus Terbanyak : " + stat.getJenisNarkotikaTerbanyak() + "\n\n");
            writer.write("=== DAFTAR PUTUSAN (Diurutkan Berdasarkan Berat Bukti Terbesar) ===\n");

            Collections.sort(daftar);
            for (Putusan p : daftar) {
                writer.write(p.toString() + "\n");
            }

            tampilkanAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diekspor ke Laporan_Statistik_KMS.txt (Poin Bonus +2!)");
        } catch (Exception e) {
            tampilkanAlert(Alert.AlertType.ERROR, "Error", "Gagal mengekspor file: " + e.getMessage());
        }
    }

    private void perbaruiTabel() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
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