package kms_java.model;

public class Putusan extends DokumenHukum implements Comparable<Putusan> {
    private String nomorPerkara;
    private String pengadilan;
    private String tanggalPutusan;
    private String namaTerdakwa;
    private int umurTerdakwa;
    private String jenisNarkotika;
    private double beratBarangBukti;
    private String pasalDilanggar;
    private String peranTerdakwa;
    private int vonisHukuman;
    private double vonisDenda;
    private String namaHakim;

    private static int jumlahDibuat = 0;

    public Putusan() {
        super("Belum Ada Nomor");
        this.nomorPerkara = "Belum Ada Nomor";
        this.namaTerdakwa = "Belum Diketahui";
        this.jenisNarkotika = "Belum Diketahui";
        this.pengadilan = "PN Default";
        this.tanggalPutusan = "Belum Diketahui";
        this.peranTerdakwa = "Terdakwa";
        this.pasalDilanggar = "Belum Diketahui";
        this.namaHakim = "Belum Diketahui";
        this.umurTerdakwa = 0;
        this.vonisHukuman = 0;
        this.vonisDenda = 0.0;
        this.beratBarangBukti = 0.0;
        jumlahDibuat++;
    }

    public Putusan(String nomorPerkara, String pengadilan, String tanggalPutusan, String namaTerdakwa,
                   int umurTerdakwa, String jenisNarkotika, double beratBarangBukti, String pasalDilanggar,
                   String peranTerdakwa, int vonisHukuman, double vonisDenda, String namaHakim) {
        super(nomorPerkara);
        this.nomorPerkara = nomorPerkara;
        this.pengadilan = pengadilan;
        this.tanggalPutusan = tanggalPutusan;
        this.namaTerdakwa = namaTerdakwa;
        this.umurTerdakwa = umurTerdakwa;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBukti = beratBarangBukti;
        this.pasalDilanggar = pasalDilanggar;
        this.peranTerdakwa = peranTerdakwa;
        this.vonisHukuman = vonisHukuman;
        this.vonisDenda = vonisDenda;
        this.namaHakim = namaHakim;
        jumlahDibuat++;
    }

    public String getNomorPerkara() { return nomorPerkara; }
    public void setNomorPerkara(String nomorPerkara) { this.nomorPerkara = nomorPerkara; }

    public String getPengadilan() { return pengadilan; }
    public void setPengadilan(String pengadilan) { this.pengadilan = pengadilan; }

    public String getTanggalPutusan() { return tanggalPutusan; }
    public void setTanggalPutusan(String tanggalPutusan) { this.tanggalPutusan = tanggalPutusan; }

    public String getNamaTerdakwa() { return namaTerdakwa; }
    public void setNamaTerdakwa(String namaTerdakwa) { this.namaTerdakwa = namaTerdakwa; }

    public int getUmurTerdakwa() { return umurTerdakwa; }
    public void setUmurTerdakwa(int umurTerdakwa) { this.umurTerdakwa = umurTerdakwa; }

    public String getJenisNarkotika() { return jenisNarkotika; }
    public void setJenisNarkotika(String jenisNarkotika) { this.jenisNarkotika = jenisNarkotika; }

    public double getBeratBarangBukti() { return beratBarangBukti; }
    public void setBeratBarangBukti(double beratBarangBukti) { this.beratBarangBukti = beratBarangBukti; }

    public String getPasalDilanggar() { return pasalDilanggar; }
    public void setPasalDilanggar(String pasalDilanggar) { this.pasalDilanggar = pasalDilanggar; }

    public String getPeranTerdakwa() { return peranTerdakwa; }
    public void setPeranTerdakwa(String peranTerdakwa) { this.peranTerdakwa = peranTerdakwa; }

    public int getVonisHukuman() { return vonisHukuman; }
    public void setVonisHukuman(int vonisHukuman) { this.vonisHukuman = vonisHukuman; }

    public double getVonisDenda() { return vonisDenda; }
    public void setVonisDenda(double vonisDenda) { this.vonisDenda = vonisDenda; }

    public String getNamaHakim() { return namaHakim; }
    public void setNamaHakim(String namaHakim) { this.namaHakim = namaHakim; }

    public static int getJumlahDibuat() {
        return jumlahDibuat;
    }

    public void tampilkan() {
        System.out.println("No: " + nomorPerkara + " | Terdakwa: " + namaTerdakwa);
    }

    public void tampilkan(boolean detail) {
        if (detail) {
            System.out.println(toString());
        } else {
            tampilkan();
        }
    }

    public String getKategoriHukuman() {
        if (vonisHukuman < 12) return "Ringan";
        if (vonisHukuman <= 60) return "Sedang";
        return "Berat";
    }

    @Override
    public String dapatkanRingkasan() {
        return "Putusan No: " + nomorPerkara + " | Terdakwa: " + namaTerdakwa;
    }

    @Override
    public String toString() {
        return "Putusan{" + "No='" + nomorPerkara + '\'' + ", Terdakwa='" + namaTerdakwa + '\'' +
                ", Jenis='" + jenisNarkotika + '\'' + ", Berat=" + beratBarangBukti + "g, Vonis=" + vonisHukuman + " bulan}";
    }

    @Override
    public int compareTo(Putusan lainnya) {
        return Double.compare(lainnya.getBeratBarangBukti(), this.beratBarangBukti);
    }
}