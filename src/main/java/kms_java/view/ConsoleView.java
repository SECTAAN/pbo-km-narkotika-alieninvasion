package kms_java.view;


public class ConsoleView implements IViewLayer {



    public ConsoleView() {
    }


    @Override
    public void tampilkanPesan(String pesan) {
        System.out.println(">> " + pesan);
    }

    @Override
    public void bersihkanLayar() {

        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }


    public void tampilkanHeader() {
        System.out.println("===============================================");
        System.out.println(" KMS PUTUSAN PENGADILAN NARKOTIKA (CLI MODE)   ");
        System.out.println("===============================================");
    }

    public void tampilkanMenuUtama() {
        System.out.println("\nMenu Utama:");
        System.out.println("1. Muat Dataset dari Folder PDF");
        System.out.println("2. Tampilkan Seluruh Data Putusan");
        System.out.println("3. Analisis & Statistik Data");
        System.out.println("4. Keluar dari Aplikasi");
        System.out.print("Pilih menu (1-4): ");
    }
}