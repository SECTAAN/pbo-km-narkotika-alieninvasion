package kms_java.model;

import java.util.ArrayList;

public class KnowledgeRepository {
    private ArrayList<Putusan> daftarPutusan;

    public KnowledgeRepository() {
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