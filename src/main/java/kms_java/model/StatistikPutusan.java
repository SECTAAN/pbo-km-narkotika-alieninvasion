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
}
