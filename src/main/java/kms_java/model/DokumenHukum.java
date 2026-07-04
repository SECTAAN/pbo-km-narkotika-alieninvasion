package kms_java.model;

public abstract class DokumenHukum {
    protected String nomorPutusan;

    public DokumenHukum(String nomorPutusan) {
        this.nomorPutusan = nomorPutusan;
    }

    public String getNomorPutusan() { return nomorPutusan; }

    public abstract String dapatkanRingkasan();
}