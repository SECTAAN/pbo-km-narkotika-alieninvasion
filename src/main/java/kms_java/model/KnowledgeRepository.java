package kms_java.model;

import java.util.ArrayList;

public class KnowledgeRepository {
    private ArrayList<Putusan> daftarPutusan = new ArrayList<>();

    public void tambahData(Putusan p) {
        daftarPutusan.add(p);
    }

    public ArrayList<Putusan> getSemuaData() {
        return daftarPutusan;
    }

    public int getTotalData() {
        return daftarPutusan.size();
    }

    public boolean hapus(String nomor) {
        for (int i = 0; i < daftarPutusan.size(); i++) {
            if (daftarPutusan.get(i).getNomorPutusan().equalsIgnoreCase(nomor)) {
                daftarPutusan.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Putusan> cari(String keyword) {
        ArrayList<Putusan> hasil = new ArrayList<>();
        for (Putusan p : daftarPutusan) {
            if (p.getNomorPutusan().toLowerCase().contains(keyword.toLowerCase()) ||
                    p.getNamaTerdakwa().toLowerCase().contains(keyword.toLowerCase())) {
                hasil.add(p);
            }
        }
        return hasil;
    }
}