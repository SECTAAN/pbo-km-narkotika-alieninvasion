package kms_java.model;

public class Putusan extends DokumenHukum implements Comparable<Putusan> {
    private String namaTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasal;
    private String vonis;

    private String pengadilan;
    private String tanggalPutusan;
    private int umurTerdakwa;
    private String peranTerdakwa;
    private double vonisDenda;
    private String namaHakim;

    private static int jumlahDibuat = 0;

    public Putusan() {
        super("Belum Ada Nomor");
        this.namaTerdakwa = "Belum Diketahui";
        this.jenisNarkotika = "Belum Diketahui";
        this.pengadilan = "PN Default";
        this.tanggalPutusan = "Belum Diketahui";
        this.peranTerdakwa = "Terdakwa";
        this.namaHakim = "Belum Diketahui";
        jumlahDibuat++;
    }

    public Putusan(String nomorPutusan, String namaTerdakwa, String jenisNarkotika, double beratBarangBukti, String pasal, String vonis) {
        super(nomorPutusan);
        this.namaTerdakwa = namaTerdakwa;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBukti = beratBarangBukti;
        this.pasal = pasal;
        this.vonis = vonis;

        this.pengadilan = "PN Default";
        this.tanggalPutusan = "Belum Diketahui";
        this.umurTerdakwa = 0;
        this.peranTerdakwa = "Terdakwa";
        this.vonisDenda = 0.0;
        this.namaHakim = "Belum Diketahui";

        jumlahDibuat++;
    }

    public void tampilkan() {
        System.out.println("No: " + nomorPutusan + " | Terdakwa: " + namaTerdakwa);
    }

    public void tampilkan(boolean detail) {
        if (detail) {
            System.out.println("No: " + nomorPutusan + "\nTerdakwa: " + namaTerdakwa + "\nNarkotika: " + jenisNarkotika + " (" + beratBarangBukti + "g)");
        } else {
            tampilkan();
        }
    }

    public String getNamaTerdakwa() { return namaTerdakwa; }
    public String getJenisNarkotika() { return jenisNarkotika; }
    public double getBeratBarangBukti() { return beratBarangBukti; }
    public String getPasal() { return pasal; }
    public String getVonis() { return vonis; }
    public String getPengadilan() { return pengadilan; }
    public String getTanggalPutusan() { return tanggalPutusan; }
    public int getUmurTerdakwa() { return umurTerdakwa; }
    public String getPeranTerdakwa() { return peranTerdakwa; }
    public double getVonisDenda() { return vonisDenda; }
    public String getNamaHakim() { return namaHakim; }

    public static int getJumlahDibuat() {
        return jumlahDibuat;
    }

    @Override
    public String dapatkanRingkasan() {
        return "Putusan No: " + nomorPutusan + " | Terdakwa: " + namaTerdakwa;
    }

    @Override
    public int compareTo(Putusan lainnya) {
        return Double.compare(lainnya.getBeratBarangBukti(), this.beratBarangBukti);
    }
}