package kms_java.model;

import java.util.ArrayList;

public class StatistikPutusan {
    private int totalPutusan;
    private double rataRataVonis;
    private double rataRataDenda;
    private String jenisNarkotikaTerbanyak;
    private String[] distribusiPeran;

    private ArrayList<Putusan> daftar;

    public StatistikPutusan(ArrayList<Putusan> daftar) {
        this.daftar = daftar;
        this.distribusiPeran = new String[]{"Bandar: 0", "Kurir: 0", "Pengguna: 0", "Lainnya: 0"};
    }

    public void hitungSemua() {
        if (daftar == null || daftar.isEmpty()) {
            this.totalPutusan = 0;
            return;
        }

        this.totalPutusan = daftar.size();
        double totalVonis = 0;
        double totalDenda = 0;
        int sabu = 0, ganja = 0, ekstasi = 0;

        int countBandar = 0, countKurir = 0, countPengguna = 0, countLainnya = 0;

        for (Putusan p : daftar) {
            totalVonis += p.getVonisHukuman();
            totalDenda += p.getVonisDenda();

            String jenis = (p.getJenisNarkotika() != null) ? p.getJenisNarkotika().toLowerCase() : "";
            if (jenis.contains("sabu")) sabu++;
            else if (jenis.contains("ganja")) ganja++;
            else if (jenis.contains("ekstasi")) ekstasi++;

            String peran = (p.getPeranTerdakwa() != null) ? p.getPeranTerdakwa().toLowerCase() : "";
            if (peran.contains("bandar")) countBandar++;
            else if (peran.contains("kurir")) countKurir++;
            else if (peran.contains("pengguna")) countPengguna++;
            else countLainnya++;
        }

        this.rataRataVonis = Math.round((totalVonis / totalPutusan) * 100.0) / 100.0;
        this.rataRataDenda = Math.round((totalDenda / totalPutusan) * 100.0) / 100.0;

        if (sabu >= ganja && sabu >= ekstasi) this.jenisNarkotikaTerbanyak = "Sabu-sabu";
        else if (ganja >= sabu && ganja >= ekstasi) this.jenisNarkotikaTerbanyak = "Ganja";
        else this.jenisNarkotikaTerbanyak = "Ekstasi";

        this.distribusiPeran[0] = "Bandar: " + countBandar;
        this.distribusiPeran[1] = "Kurir: " + countKurir;
        this.distribusiPeran[2] = "Pengguna: " + countPengguna;
        this.distribusiPeran[3] = "Lainnya: " + countLainnya;
    }

    public void tampilkanLaporan() {
        System.out.println("=== LAPORAN STATISTIK ===");
        System.out.println("Total Putusan : " + totalPutusan);
        System.out.println("Rata Vonis    : " + rataRataVonis + " bulan");
        System.out.println("Narkotika Top : " + jenisNarkotikaTerbanyak);
        System.out.println("Distribusi Peran:");
        for (String peran : distribusiPeran) {
            System.out.println("- " + peran);
        }
    }

    public int getTotalPutusan() { return totalPutusan; }
    public double getRataRataVonis() { return rataRataVonis; }
    public double getRataRataDenda() { return rataRataDenda; }
    public String getJenisNarkotikaTerbanyak() { return jenisNarkotikaTerbanyak; }
    public String[] getDistribusiPeran() { return distribusiPeran; }
}