package kms_java.controller;

import kms_java.model.Putusan;
import kms_java.view.ConsoleView;
import java.util.ArrayList;

public class KmsController {
    private ConsoleView view;
    private ArrayList<Putusan> databasePutusan;

    public KmsController() {
        this.view = new ConsoleView();
        this.databasePutusan = new ArrayList<>();
    }

    public void mulaiAplikasi() {
        view.tampilkanPesan("Sistem KMS Narkotika Memulai...");
        view.tampilkanMenuUtama();
    }
}