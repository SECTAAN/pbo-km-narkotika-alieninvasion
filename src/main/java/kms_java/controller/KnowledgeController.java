package kms_java.controller;

import kms_java.model.KnowledgeRepository;
import kms_java.model.Putusan;
import kms_java.model.StatistikPutusan;
import kms_java.util.InputHandler;
import kms_java.util.PdfReader;
import kms_java.view.ConsoleView;

import java.io.File;
import java.util.ArrayList;

public class KnowledgeController {
    private KnowledgeRepository repository;
    private StatistikPutusan statistik;
    private ConsoleView view;
    private InputHandler inputHandler;
    private PdfReader pdfReader;

    public KnowledgeController() {
        this.repository = new KnowledgeRepository();
        this.statistik = new StatistikPutusan();
        this.view = new ConsoleView();
        this.inputHandler = new InputHandler();
        this.pdfReader = new PdfReader();
    }

    public void mulaiAplikasi() {
        boolean isRunning = true;

        view.bersihkanLayar();
        view.tampilkanHeader();

        while (isRunning) {
            view.tampilkanMenuUtama();

            int pilihan = inputHandler.ambilInputAngka();

            switch (pilihan) {
                case 1:
                    muatDatasetPdf();
                    break;
                case 2:
                    tampilkanData();
                    break;
                case 3:
                    tampilkanStatistik();
                    break;
                case 4:
                    view.tampilkanPesan("Terima kasih. Program dihentikan.");
                    isRunning = false;
                    break;
                default:
                    view.tampilkanPesan("[ERROR] Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void muatDatasetPdf() {
        view.tampilkanPesan("Masukkan lokasi folder tempat PDF berada (contoh: C:/Tugas/PDF): ");
        String pathFolder = inputHandler.ambilInputTeks();
        File folder = new File(pathFolder);

        if (folder.exists() && folder.isDirectory()) {
            File[] daftarFile = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (daftarFile != null && daftarFile.length > 0) {
                view.tampilkanPesan("Menemukan " + daftarFile.length + " file PDF. Memulai ekstraksi...");
                int sukses = 0;

                for (File file : daftarFile) {
                    Putusan putusanBaru = pdfReader.prosesPdfKeObjek(file.getAbsolutePath());
                    if (putusanBaru != null) {
                        repository.tambahData(putusanBaru);
                        sukses++;
                    }
                }
                view.tampilkanPesan("Berhasil memuat " + sukses + " data putusan ke dalam memori!");
            } else {
                view.tampilkanPesan("[ERROR] Folder ditemukan, tapi tidak ada file berakhiran .pdf di dalamnya.");
            }
        } else {
            view.tampilkanPesan("[ERROR] Folder tidak ditemukan. Pastikan path yang diketik benar.");
        }
    }

    private void tampilkanData() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            view.tampilkanPesan("Belum ada data. Silakan muat dataset PDF (Menu 1) terlebih dahulu.");
        } else {
            view.tampilkanPesan("\n--- DAFTAR PUTUSAN ---");
            for (Putusan p : daftar) {
                view.tampilkanPesan("- No: " + p.getNomorPutusan() + " | Terdakwa: " + p.getNamaTerdakwa() + " | Vonis: " + p.getVonis());
            }
            view.tampilkanPesan("Total Data: " + repository.getTotalData());
        }
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            view.tampilkanPesan("Belum ada data. Silakan muat dataset PDF (Menu 1) terlebih dahulu.");
            return;
        }

        view.tampilkanPesan("\n--- ANALISIS STATISTIK ---");
        double rataRata = statistik.hitungRataRataBerat(daftar);
        view.tampilkanPesan("Rata-rata berat barang bukti dari seluruh kasus: " + rataRata + " gram");

        int kasusSabu = statistik.hitungTotalPerJenis(daftar, "Sabu");
        int kasusGanja = statistik.hitungTotalPerJenis(daftar, "Ganja");
        view.tampilkanPesan("Total kasus yang melibatkan Sabu-sabu: " + kasusSabu + " kasus");
        view.tampilkanPesan("Total kasus yang melibatkan Ganja: " + kasusGanja + " kasus");
    }
}