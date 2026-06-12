package kms_java.controller;

import kms_java.model.Putusan;
import kms_java.view.MainView;
import java.util.ArrayList;

public class KmsController {
    private MainView view;
    private ArrayList<Putusan> databasePutusan;

    public KmsController() {
        this.view = new MainView();
        this.databasePutusan = new ArrayList<>();
    }

    public void mulaiAplikasi() {
        view.tampilkanPesan("Sistem KMS Narkotika Memulai...");
        view.tampilkanMenuUtama();
    }
}