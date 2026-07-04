package kms_java.controller;

import kms_java.model.KnowledgeRepository;
import kms_java.model.Putusan;
import kms_java.model.StatistikPutusan;
import kms_java.util.InputHandler;
import kms_java.util.PdfReader;
import kms_java.view.JavaFXView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        guiView.getBtnTambahManual().setOnAction(e -> tambahDataManual());
        guiView.getBtnImport().setOnAction(e -> importDataCsvTxt());
        guiView.getBtnStatistik().setOnAction(e -> tampilkanStatistik());
        guiView.getBtnHapus().setOnAction(e -> hapusData());
        guiView.getBtnClear().setOnAction(e -> bersihkanTabel());
        guiView.getBtnCari().setOnAction(e -> cariData());
        guiView.getBtnExport().setOnAction(e -> exportKeTxt());
        guiView.getBtnFilter().setOnAction(e -> filterDataLanjutan());
    }

    private void muatDatasetPdf() {
        File folder = new File("pdf-putusan");
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
                guiView.tampilkanPesanAlert("INFO", "Sukses", "Berhasil memuat " + sukses + " data putusan!");
            } else {
                guiView.tampilkanPesanAlert("WARNING", "Peringatan", "Folder 'pdf-putusan' kosong.");
            }
        } else {
            guiView.tampilkanPesanAlert("ERROR", "Error", "Folder 'pdf-putusan' tidak ditemukan!");
        }
    }

    private void tambahDataManual() {
        String[] hasilInput = guiView.tampilkanFormManualLengkap();
        if (hasilInput != null) {
            try {
                String nomor = hasilInput[0].trim();
                String nama = hasilInput[1].trim();
                String jenis = hasilInput[2].trim();

                double berat = InputHandler.validasiDoubleGUI(hasilInput[3]);
                int vonis = InputHandler.validasiIntGUI(hasilInput[4]);
                double denda = InputHandler.validasiDoubleGUI(hasilInput[5]);

                Putusan pBaru = new Putusan(nomor, "PN Default", "2026", nama, 30, jenis, berat, "Pasal 114", "Bandar", vonis, denda, "Majelis Hakim");
                repository.simpan(pBaru);
                perbaruiTabel();
                guiView.tampilkanPesanAlert("INFO", "Sukses", "Data manual berhasil ditambahkan!");
            } catch (NumberFormatException ex) {
                guiView.tampilkanPesanAlert("ERROR", "Error Validasi Input", "Kolom Berat, Vonis, dan Denda harus berupa ANGKA!\n" + ex.getMessage());
            } catch (Exception ex) {
                guiView.tampilkanPesanAlert("ERROR", "Error Sistem", ex.getMessage());
            }
        }
    }

    private void importDataCsvTxt() {
        File file = guiView.tampilkanPilihFile();
        if (file == null) return;

        int sukses = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String baris;
            Pattern patternTxt = Pattern.compile("Putusan\\{No='(.*?)', Terdakwa='(.*?)', Jenis='(.*?)', Berat=(.*?)g, Vonis=(.*?) bulan\\}");

            while ((baris = br.readLine()) != null) {
                if (baris.trim().isEmpty() || baris.startsWith("===")) continue;

                if (baris.startsWith("Putusan{")) {
                    Matcher m = patternTxt.matcher(baris);
                    if (m.find()) {
                        String no = m.group(1);
                        String terdakwa = m.group(2);
                        String jenis = m.group(3);
                        double berat = InputHandler.validasiDoubleGUI(m.group(4));
                        int vonis = InputHandler.validasiIntGUI(m.group(5));

                        repository.simpan(new Putusan(no, "PN Default", "2026", terdakwa, 30, jenis, berat, "Pasal 114", "Bandar", vonis, 800000000.0, "Majelis Hakim"));
                        sukses++;
                    }
                } else if (baris.contains(";") || baris.contains(",")) {
                    String[] data = baris.split("[;,]");
                    if (data.length >= 6) {
                        double berat = InputHandler.validasiDoubleGUI(data[3]);
                        int vonis = InputHandler.validasiIntGUI(data[4]);
                        double denda = InputHandler.validasiDoubleGUI(data[5]);

                        repository.simpan(new Putusan(data[0], "PN Default", "2026", data[1], 30, data[2], berat, "Pasal 114", "Bandar", vonis, denda, "Majelis Hakim"));
                        sukses++;
                    }
                }
            }
            perbaruiTabel();
            guiView.tampilkanPesanAlert("INFO", "Import Sukses", "Berhasil mengimpor " + sukses + " data!");
        } catch (Exception e) {
            guiView.tampilkanPesanAlert("ERROR", "Error Import", "Gagal membaca file: " + e.getMessage());
        }
    }

    private void hapusData() {
        Putusan terpilih = guiView.getTable().getSelectionModel().getSelectedItem();
        if (terpilih != null) {
            boolean sukses = repository.hapus(terpilih.getNomorPerkara());
            if (sukses) {
                perbaruiTabel();
                guiView.tampilkanPesanAlert("INFO", "Sukses", "Data berhasil dihapus!");
            }
        } else {
            guiView.tampilkanPesanAlert("WARNING", "Peringatan", "Pilih baris data terlebih dahulu!");
        }
    }

    private void bersihkanTabel() {
        repository = new KnowledgeRepository();
        perbaruiTabel();
        guiView.tampilkanPesanAlert("INFO", "Sukses", "Data tabel telah dibersihkan.");
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
            guiView.perbaruiDataTabel(hasilCariNama, "Hasil pencarian: " + hasilCariNama.size() + " data ditemukan");
        } else {
            perbaruiTabel();
        }
    }

    private void filterDataLanjutan() {
        String filterJenis = guiView.getCmbJenis().getValue();
        String filterPengadilan = guiView.getCmbPengadilan().getValue();
        String filterVonis = guiView.getCmbVonis().getValue();

        ArrayList<Putusan> semuaData = repository.getDaftarSemua();
        ArrayList<Putusan> hasilFilter = new ArrayList<>();

        for (Putusan p : semuaData) {
            boolean lolosJenis = false;
            if (filterJenis.equals("Semua Jenis")) {
                lolosJenis = true;
            } else if (p.getJenisNarkotika().toLowerCase().contains(filterJenis.toLowerCase())) {
                lolosJenis = true;
            }

            boolean lolosPengadilan = false;
            if (filterPengadilan.equals("Semua Pengadilan")) {
                lolosPengadilan = true;
            } else if (filterPengadilan.equals("PN Surabaya") && p.getPengadilan().toLowerCase().contains("surabaya")) {
                lolosPengadilan = true;
            } else if (filterPengadilan.equals("PN Lainnya") && !p.getPengadilan().toLowerCase().contains("surabaya")) {
                lolosPengadilan = true;
            }

            boolean lolosVonis = false;
            int vonis = p.getVonisHukuman();
            if (filterVonis.equals("Semua Rentang")) {
                lolosVonis = true;
            } else if (filterVonis.equals("Ringan (< 12 Bulan)") && vonis < 12) {
                lolosVonis = true;
            } else if (filterVonis.equals("Sedang (12 - 60 Bulan)") && vonis >= 12 && vonis <= 60) {
                lolosVonis = true;
            } else if (filterVonis.equals("Berat (> 60 Bulan)") && vonis > 60) {
                lolosVonis = true;
            }

            if (lolosJenis && lolosPengadilan && lolosVonis) {
                hasilFilter.add(p);
            }
        }

        Collections.sort(hasilFilter);
        guiView.perbaruiDataTabel(hasilFilter, "Hasil Filter: " + hasilFilter.size() + " data ditemukan");
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
        if (daftar.isEmpty()) {
            guiView.tampilkanPesanAlert("WARNING", "Peringatan", "Data kosong.");
            return;
        }
        StatistikPutusan stat = new StatistikPutusan(daftar);
        stat.hitungSemua();
        StringBuilder pesan = new StringBuilder();
        pesan.append("Total Data Putusan: ").append(stat.getTotalPutusan()).append(" Kasus\n");
        pesan.append("Rata-rata Vonis: ").append(stat.getRataRataVonis()).append(" Bulan\n");
        pesan.append("Jenis Terbanyak: ").append(stat.getJenisNarkotikaTerbanyak()).append("\n\n");
        pesan.append("--- Distribusi Peran ---\n");
        for (String peran : stat.getDistribusiPeran()) {
            pesan.append("- ").append(peran).append("\n");
        }
        guiView.tampilkanPesanAlert("INFO", "Analisis Statistik", pesan.toString());
    }

    private void exportKeTxt() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
        if (daftar.isEmpty()) return;
        StatistikPutusan stat = new StatistikPutusan(daftar);
        stat.hitungSemua();
        try (FileWriter writer = new FileWriter("Laporan_Statistik_KMS.txt")) {
            writer.write("=== LAPORAN STATISTIK ===\n");
            Collections.sort(daftar);
            for (Putusan p : daftar) writer.write(p.toString() + "\n");
            guiView.tampilkanPesanAlert("INFO", "Sukses", "Data berhasil diekspor (Poin Bonus +2!)");
        } catch (Exception e) {
            guiView.tampilkanPesanAlert("ERROR", "Error", "Gagal ekspor: " + e.getMessage());
        }
    }

    private void perbaruiTabel() {
        ArrayList<Putusan> daftar = repository.getDaftarSemua();
        Collections.sort(daftar);
        guiView.perbaruiDataTabel(daftar, "Total Data Tersimpan: " + daftar.size() + " | Memori Siap");
    }
}