package kms_java.model;

public class Putusan {
    private String nomorPutusan;
    private String namaTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasal;
    private String vonis;

    public Putusan() {}

    public Putusan(String nomorPutusan, String namaTerdakwa, String jenisNarkotika, double beratBarangBukti, String pasal, String vonis) {
        this.nomorPutusan = nomorPutusan;
        this.namaTerdakwa = namaTerdakwa;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBukti = beratBarangBukti;
        this.pasal = pasal;
        this.vonis = vonis;
    }

    public String getNomorPutusan() { return nomorPutusan; }
    public void setNomorPutusan(String nomorPutusan) { this.nomorPutusan = nomorPutusan; }

    public String getNamaTerdakwa() { return namaTerdakwa; }
    public void setNamaTerdakwa(String namaTerdakwa) { this.namaTerdakwa = namaTerdakwa; }

    public String getJenisNarkotika() { return jenisNarkotika; }
    public void setJenisNarkotika(String jenisNarkotika) { this.jenisNarkotika = jenisNarkotika; }

    public double getBeratBarangBukti() { return beratBarangBukti; }
    public void setBeratBarangBukti(double beratBarangBukti) { this.beratBarangBukti = beratBarangBukti; }

    public String getPasal() { return pasal; }
    public void setPasal(String pasal) { this.pasal = pasal; }

    public String getVonis() { return vonis; }
    public void setVonis(String vonis) { this.vonis = vonis; }
}