package kms_java.controller;

import kms_java.model.Putusan;
import kms_java.model.KnowledgeRepository;
import kms_java.view.ConsoleView;
import java.util.ArrayList;

public class KnowledgeController {
    private ConsoleView view;
    private KnowledgeRepository repository;

    public KnowledgeController() {
        this.view = new ConsoleView();
        this.repository = new KnowledgeRepository();
    }

    public void mulaiAplikasi() {
        view.tampilkanPesan("Sistem KMS Narkotika Memulai...\n");
        boolean isRunning = true;

        while (isRunning) {
            view.tampilkanMenuUtama();
            int pilihan = view.ambilPilihanMenu();

            switch (pilihan) {
                case 1:
                    tambahDataPutusan();
                    break;
                case 2:
                    tampilkanSemuaData();
                    break;
                case 3:
                    cariDataPutusan();
                    break;
                case 4:
                    view.tampilkanPesan("Terima kasih telah menggunakan sistem KMS. Program dihentikan.");
                    isRunning = false;
                    break;
                default:
                    view.tampilkanPesan("[ERROR] Pilihan tidak ada di menu. Silakan coba lagi.");
            }
        }
    }

    private void tambahDataPutusan() {
        view.tampilkanPesan("--- TAMBAH DATA PUTUSAN ---");
        String nomor = view.ambilInputTeks("Nomor Putusan");
        String terdakwa = view.ambilInputTeks("Nama Terdakwa");
        String jenis = view.ambilInputTeks("Jenis Narkotika");
        double berat = view.ambilInputAngkaDesimal("Berat Barang Bukti (Gram)");
        String pasal = view.ambilInputTeks("Pasal yang Dilanggar");
        String vonis = view.ambilInputTeks("Vonis Hukuman");

        Putusan putusanBaru = new Putusan(nomor, terdakwa, jenis, berat, pasal, vonis);
        repository.tambahData(putusanBaru);

        view.tampilkanPesan("Data putusan berhasil ditambahkan ke dalam sistem!");
    }

    private void tampilkanSemuaData() {
        view.tampilkanPesan("--- DAFTAR SELURUH PUTUSAN ---");
        ArrayList<Putusan> daftar = repository.getSemuaData();

        if (daftar.isEmpty()) {
            view.tampilkanPesan("Belum ada data putusan di dalam sistem.");
        } else {
            for (Putusan p : daftar) {
                System.out.println("- No: " + p.getNomorPutusan() +
                        " | Terdakwa: " + p.getNamaTerdakwa() +
                        " | Narkotika: " + p.getJenisNarkotika() +
                        " (" + p.getBeratBarangBukti() + "g) | Vonis: " + p.getVonis());
            }
            view.tampilkanPesan("Total Data: " + repository.getTotalData());
        }
    }

    private void cariDataPutusan() {
        view.tampilkanPesan("--- PENCARIAN PUTUSAN ---");
        String keyword = view.ambilInputTeks("Masukkan Nomor Putusan atau Nama Terdakwa");
        boolean ditemukan = false;

        for (Putusan p : repository.getSemuaData()) {
            if (p.getNomorPutusan().toLowerCase().contains(keyword.toLowerCase()) ||
                    p.getNamaTerdakwa().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println("\n[DATA DITEMUKAN]");
                System.out.println("Nomor Putusan : " + p.getNomorPutusan());
                System.out.println("Terdakwa      : " + p.getNamaTerdakwa());
                System.out.println("Narkotika     : " + p.getJenisNarkotika() + " (" + p.getBeratBarangBukti() + " gram)");
                System.out.println("Pasal         : " + p.getPasal());
                System.out.println("Vonis         : " + p.getVonis());
                ditemukan = true;
            }
        }

        if (!ditemukan) {
            view.tampilkanPesan("Data dengan kata kunci '" + keyword + "' tidak ditemukan.");
        }
    }
}