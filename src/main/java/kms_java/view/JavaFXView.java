package kms_java.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kms_java.model.Putusan;

public class JavaFXView {
    private BorderPane mainLayout;
    private TableView<Putusan> table;
    private Button btnLoadPdf;
    private Button btnStatistik;
    private Button btnHapus;
    private Button btnClear;
    private Button btnExport;

    private TextField txtCari;
    private Button btnCari;


    private Label lblStatus;

    public JavaFXView() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label lblTitle = new Label("KMS Putusan Pengadilan Narkotika (GUI)");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        btnLoadPdf = new Button("Muat Data PDF dari Folder");
        btnLoadPdf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        btnClear = new Button("Bersihkan Tabel");
        btnClear.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");

        btnStatistik = new Button("Tampilkan Analisis Statistik");
        btnStatistik.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        btnHapus = new Button("Hapus Data Terpilih");
        btnHapus.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnExport = new Button("Export TXT");
        btnExport.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox boxAksiData = new HBox(10, btnLoadPdf, btnClear);
        HBox boxAksiLanjutan = new HBox(10, btnStatistik, btnHapus, btnExport);

        VBox topBox = new VBox(10, lblTitle, boxAksiData, boxAksiLanjutan);
        topBox.setPadding(new Insets(0, 0, 10, 0));

        txtCari = new TextField();
        txtCari.setPromptText("Cari nama terdakwa atau nomor...");
        txtCari.setPrefWidth(250);

        btnCari = new Button("Cari Data");
        btnCari.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox searchBox = new HBox(10, new Label("Pencarian:"), txtCari, btnCari);
        searchBox.setPadding(new Insets(0, 0, 15, 0));

        VBox kombinasiHeader = new VBox(10, topBox, searchBox);
        mainLayout.setTop(kombinasiHeader);

        table = new TableView<>();

        TableColumn<Putusan, String> colNomor = new TableColumn<>("Nomor Perkara");
        colNomor.setCellValueFactory(new PropertyValueFactory<>("nomorPerkara"));
        colNomor.setPrefWidth(150);

        TableColumn<Putusan, String> colTerdakwa = new TableColumn<>("Nama Terdakwa");
        colTerdakwa.setCellValueFactory(new PropertyValueFactory<>("namaTerdakwa"));
        colTerdakwa.setPrefWidth(200);

        TableColumn<Putusan, String> colJenis = new TableColumn<>("Jenis Narkotika");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenisNarkotika"));
        colJenis.setPrefWidth(120);

        TableColumn<Putusan, Double> colBerat = new TableColumn<>("Berat (g)");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("beratBarangBukti"));
        colBerat.setPrefWidth(80);

        TableColumn<Putusan, Integer> colVonis = new TableColumn<>("Vonis (Bulan)");
        colVonis.setCellValueFactory(new PropertyValueFactory<>("vonisHukuman"));
        colVonis.setPrefWidth(100);

        TableColumn<Putusan, Double> colDenda = new TableColumn<>("Denda (Rp)");
        colDenda.setCellValueFactory(new PropertyValueFactory<>("vonisDenda"));
        colDenda.setPrefWidth(150);

        colDenda.setCellFactory(tc -> new TableCell<Putusan, Double>() {
            @Override
            protected void updateItem(Double denda, boolean empty) {
                super.updateItem(denda, empty);
                if (empty || denda == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f", denda));
                }
            }
        });

        table.getColumns().addAll(colNomor, colTerdakwa, colJenis, colBerat, colVonis, colDenda);
        mainLayout.setCenter(table);

        lblStatus = new Label("Total Data: 0 | Siap Digunakan");
        lblStatus.setStyle("-fx-font-weight: bold; -fx-padding: 10px 0 0 0; -fx-text-fill: #7f8c8d;");
        mainLayout.setBottom(lblStatus);
    }

    public BorderPane getMainLayout() { return mainLayout; }
    public TableView<Putusan> getTable() { return table; }
    public Button getBtnLoadPdf() { return btnLoadPdf; }
    public Button getBtnStatistik() { return btnStatistik; }
    public Button getBtnHapus() { return btnHapus; }
    public Button getBtnClear() { return btnClear; }
    public TextField getTxtCari() { return txtCari; }
    public Button getBtnCari() { return btnCari; }
    public Button getBtnExport() { return btnExport; }

    public Label getLblStatus() { return lblStatus; }
}