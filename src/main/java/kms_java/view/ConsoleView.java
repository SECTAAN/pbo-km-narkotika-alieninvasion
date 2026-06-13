package kms_java.view;

import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {

        this.scanner = new Scanner(System.in);
    }

    public void tampilkanMenuUtama() {
        System.out.println("\n=============================================");
        System.out.println("    KMS PUTUSAN PENGADILAN NARKOTIKA UMM     ");
        System.out.println("=============================================");
        System.out.println("1. Tambah Data Putusan Baru");
        System.out.println("2. Tampilkan Seluruh Data Putusan");
        System.out.println("3. Cari Data Putusan (Berdasarkan Nomor)");
        System.out.println("4. Keluar dari Aplikasi");
        System.out.println("=============================================");
    }


    public int ambilPilihanMenu() {
        System.out.print("Masukkan pilihan Anda (1-4): ");
        while (!scanner.hasNextInt()) {
            System.out.println("[ERROR] Input tidak valid! Harap masukkan angka.");
            scanner.next();
            System.out.print("Masukkan pilihan Anda (1-4): ");
        }
        int pilihan = scanner.nextInt();
        scanner.nextLine();
        return pilihan;
    }


    public void tampilkanPesan(String pesan) {
        System.out.println(">> " + pesan);
    }


    public String ambilInputTeks(String perintah) {
        System.out.print(perintah + ": ");
        return scanner.nextLine();
    }


    public double ambilInputAngkaDesimal(String perintah) {
        System.out.print(perintah + ": ");
        while (!scanner.hasNextDouble()) {
            System.out.println("[ERROR] Input tidak valid! Harap masukkan angka desimal (contoh: 15.5).");
            scanner.next();
            System.out.print(perintah + ": ");
        }
        double nilai = scanner.nextDouble();
        scanner.nextLine();
        return nilai;
    }
}