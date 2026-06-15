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
    private ConsoleView view; // Bisa digunakan nanti jika ada method khusus tampilan
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
        System.out.println("===============================================");
        System.out.println(" KMS PUTUSAN PENGADILAN NARKOTIKA (CLI MODE)   ");
        System.out.println("===============================================");

        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Muat Dataset dari Folder PDF");
            System.out.println("2. Tampilkan Seluruh Data Putusan");
            System.out.println("3. Analisis & Statistik Data");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");

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
                    System.out.println("Terima kasih. Program dihentikan.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void muatDatasetPdf() {
        System.out.print("Masukkan lokasi folder tempat PDF berada (contoh: C:/Tugas/PDF): ");
        String pathFolder = inputHandler.ambilInputTeks();
        File folder = new File(pathFolder);

        if (folder.exists() && folder.isDirectory()) {
            File[] daftarFile = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (daftarFile != null && daftarFile.length > 0) {
                System.out.println("Menemukan " + daftarFile.length + " file PDF. Memulai ekstraksi...");
                int sukses = 0;

                for (File file : daftarFile) {
                    Putusan putusanBaru = pdfReader.prosesPdfKeObjek(file.getAbsolutePath());
                    repository.tambahData(putusanBaru);
                    sukses++;
                }
                System.out.println("Berhasil memuat " + sukses + " data putusan ke dalam memori!");
            } else {
                System.out.println("[ERROR] Folder ditemukan, tapi tidak ada file berakhiran .pdf di dalamnya.");
            }
        } else {
            System.out.println("[ERROR] Folder tidak ditemukan. Pastikan path yang diketik benar.");
        }
    }

    private void tampilkanData() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data. Silakan muat dataset PDF (Menu 1) terlebih dahulu.");
        } else {
            System.out.println("\n--- DAFTAR PUTUSAN ---");
            for (Putusan p : daftar) {
                System.out.println("- No: " + p.getNomorPutusan() + " | Terdakwa: " + p.getNamaTerdakwa() + " | Vonis: " + p.getVonis());
            }
            System.out.println("Total Data: " + repository.getTotalData());
        }
    }

    private void tampilkanStatistik() {
        ArrayList<Putusan> daftar = repository.getSemuaData();
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data. Silakan muat dataset PDF (Menu 1) terlebih dahulu.");
            return;
        }

        System.out.println("\n--- ANALISIS STATISTIK ---");
        double rataRata = statistik.hitungRataRataBerat(daftar);
        System.out.println("Rata-rata berat barang bukti dari seluruh kasus: " + rataRata + " gram");

        int kasusSabu = statistik.hitungTotalPerJenis(daftar, "Sabu");
        int kasusGanja = statistik.hitungTotalPerJenis(daftar, "Ganja");
        System.out.println("Total kasus yang melibatkan Sabu-sabu: " + kasusSabu + " kasus");
        System.out.println("Total kasus yang melibatkan Ganja: " + kasusGanja + " kasus");
    }
}