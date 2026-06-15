package kms_java.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import kms_java.model.Putusan;

public class JavaFXView {
    private BorderPane mainLayout;
    private TableView<Putusan> table;
    private Button btnLoadPdf;
    private Button btnStatistik;

    public JavaFXView() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));


        Label lblTitle = new Label("KMS Putusan Pengadilan Narkotika (GUI)");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        btnLoadPdf = new Button("Muat Data PDF dari Folder");
        btnLoadPdf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        btnStatistik = new Button("Tampilkan Analisis Statistik");
        btnStatistik.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox topBox = new VBox(10, lblTitle, btnLoadPdf, btnStatistik);
        topBox.setPadding(new Insets(0, 0, 15, 0));
        mainLayout.setTop(topBox);


        table = new TableView<>();

        TableColumn<Putusan, String> colNomor = new TableColumn<>("Nomor Putusan");
        colNomor.setCellValueFactory(new PropertyValueFactory<>("nomorPutusan"));
        colNomor.setPrefWidth(150);

        TableColumn<Putusan, String> colTerdakwa = new TableColumn<>("Nama Terdakwa");
        colTerdakwa.setCellValueFactory(new PropertyValueFactory<>("namaTerdakwa"));
        colTerdakwa.setPrefWidth(200);

        TableColumn<Putusan, String> colJenis = new TableColumn<>("Jenis Narkotika");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenisNarkotika"));
        colJenis.setPrefWidth(150);

        TableColumn<Putusan, Double> colBerat = new TableColumn<>("Berat (g)");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("beratBarangBukti"));
        colBerat.setPrefWidth(100);

        TableColumn<Putusan, String> colVonis = new TableColumn<>("Vonis");
        colVonis.setCellValueFactory(new PropertyValueFactory<>("vonis"));
        colVonis.setPrefWidth(250);


        table.getColumns().addAll(colNomor, colTerdakwa, colJenis, colBerat, colVonis);
        mainLayout.setCenter(table);
    }


    public BorderPane getMainLayout() { return mainLayout; }
    public TableView<Putusan> getTable() { return table; }
    public Button getBtnLoadPdf() { return btnLoadPdf; }
    public Button getBtnStatistik() { return btnStatistik; }
}