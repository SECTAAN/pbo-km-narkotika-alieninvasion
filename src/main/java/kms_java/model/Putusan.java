package kms_java.model;

public class Putusan extends DokumenHukum {
    private String namaTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasal;
    private String vonis;

    public Putusan(String nomorPutusan, String namaTerdakwa, String jenisNarkotika, double beratBarangBukti, String pasal, String vonis) {
        super(nomorPutusan);
        this.namaTerdakwa = namaTerdakwa;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBukti = beratBarangBukti;
        this.pasal = pasal;
        this.vonis = vonis;
    }

    public String getNamaTerdakwa() { return namaTerdakwa; }
    public String getJenisNarkotika() { return jenisNarkotika; }
    public double getBeratBarangBukti() { return beratBarangBukti; }
    public String getPasal() { return pasal; }
    public String getVonis() { return vonis; }

    @Override
    public String dapatkanRingkasan() {
        return "Putusan No: " + nomorPutusan + " | Terdakwa: " + namaTerdakwa;
    }
}