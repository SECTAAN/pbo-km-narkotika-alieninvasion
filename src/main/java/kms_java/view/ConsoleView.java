package kms_java.view;

import java.util.Scanner;
import kms_java.util.InputHandler;

public class ConsoleView implements IViewLayer {
    public ConsoleView() {
    }

    @Override
    public void tampilkanPesan(String pesan) {
        System.out.println(">> " + pesan);
    }

    @Override
    public void bersihkanLayar() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void tampilkanHeader() {
        System.out.println("===============================================");
        System.out.println(" KMS PUTUSAN PENGADILAN NARKOTIKA (CLI MODE)   ");
        System.out.println("===============================================");
    }

    public void tampilkanMenuUtama() {
        System.out.println("\nMenu Utama:");
        System.out.println("1. Muat Dataset dari Folder PDF");
        System.out.println("2. Tampilkan Seluruh Data Putusan");
        System.out.println("3. Analisis & Statistik Data");
        System.out.println("4. Keluar dari Aplikasi");
        System.out.print("Pilih menu (1-4): ");
    }


    public String[] inputFormPutusan(Scanner sc) {
        System.out.println("\n--- FORM TAMBAH PUTUSAN ---");
        String nomor = InputHandler.validasiString("Masukkan Nomor Perkara: ", sc);
        String nama = InputHandler.validasiString("Masukkan Nama Terdakwa: ", sc);
        double berat = InputHandler.validasiDouble("Masukkan Berat Bukti (g): ", sc);
        int vonis = InputHandler.validasiInt("Masukkan Vonis (bulan): ", sc);
        double denda = InputHandler.validasiDouble("Masukkan Denda (Rp): ", sc);
        int peran = InputHandler.validasiPilihan("Pilih Peran (1.Bandar 2.Kurir 3.Pengguna): ", 1, 3, sc);

        return new String[]{nomor, nama, String.valueOf(berat), String.valueOf(vonis), String.valueOf(denda)};
    }
}