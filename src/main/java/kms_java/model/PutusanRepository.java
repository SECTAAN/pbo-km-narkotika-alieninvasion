package kms_java.model;

import java.util.ArrayList;

public class PutusanRepository {
    private ArrayList<Putusan> daftarPutusan;

    public PutusanRepository() {
        this.daftarPutusan = new ArrayList<>();
    }

    public void tambahData(Putusan putusan) {
        daftarPutusan.add(putusan);
    }

    public ArrayList<Putusan> getSemuaData() {
        return daftarPutusan;
    }

    public int getTotalData() {
        return daftarPutusan.size();
    }
}