package kms_java.model;

import java.util.ArrayList;

public class StatistikPutusan {

    public int hitungTotalPerJenis(ArrayList<Putusan> daftar, String jenis) {
        int total = 0;
        for (Putusan p : daftar) {
            if (p.getJenisNarkotika() != null && p.getJenisNarkotika().toLowerCase().contains(jenis.toLowerCase())) {
                total++;
            }
        }
        return total;
    }

    public double hitungRataRataBerat(ArrayList<Putusan> daftar) {
        if (daftar.isEmpty()) return 0.0;
        double totalBerat = 0;
        for (Putusan p : daftar) {
            totalBerat += p.getBeratBarangBukti();
        }
        return Math.round((totalBerat / daftar.size()) * 100.0) / 100.0;
    }

    public String getJenisTerbanyak(ArrayList<Putusan> daftar) {
        int sabu = hitungTotalPerJenis(daftar, "sabu");
        int ganja = hitungTotalPerJenis(daftar, "ganja");
        int ekstasi = hitungTotalPerJenis(daftar, "ekstasi");

        if (sabu >= ganja && sabu >= ekstasi) return "Sabu-sabu (" + sabu + " kasus)";
        if (ganja >= sabu && ganja >= ekstasi) return "Ganja (" + ganja + " kasus)";
        if (ekstasi >= sabu && ekstasi >= ganja) return "Ekstasi (" + ekstasi + " kasus)";

        return "Belum dapat ditentukan";
    }
}