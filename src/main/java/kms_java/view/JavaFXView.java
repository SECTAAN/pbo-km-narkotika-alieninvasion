package kms_java.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import kms_java.model.Putusan;
import java.io.File;
import java.util.List;

public class JavaFXView {
    private BorderPane mainLayout;
    private TableView<Putusan> table;
    private Button btnLoadPdf;
    private Button btnTambahManual;
    private Button btnImport;
    private Button btnStatistik;
    private Button btnHapus;
    private Button btnClear;
    private Button btnExport;
    private TextField txtCari;
    private Button btnCari;
    private Label lblStatus;
    private ComboBox<String> cmbJenis;
    private ComboBox<String> cmbPengadilan;
    private ComboBox<String> cmbVonis;
    private Button btnFilter;

    public JavaFXView() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        Label lblTitle = new Label("KMS Putusan Pengadilan Narkotika (GUI)");
        lblTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        btnLoadPdf = new Button("Muat Data PDF");
        btnLoadPdf.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        btnImport = new Button("Import CSV/TXT");
        btnImport.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");

        btnTambahManual = new Button("Tambah Manual");
        btnTambahManual.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-font-weight: bold;");

        btnClear = new Button("Bersihkan Tabel");
        btnClear.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");

        btnStatistik = new Button("Tampilkan Statistik");
        btnStatistik.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        btnHapus = new Button("Hapus Data");
        btnHapus.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnExport = new Button("Export TXT");
        btnExport.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox boxAksiData = new HBox(10, btnLoadPdf, btnImport, btnTambahManual, btnClear);
        HBox boxAksiLanjutan = new HBox(10, btnStatistik, btnHapus, btnExport);
        VBox topBox = new VBox(10, lblTitle, boxAksiData, boxAksiLanjutan);
        topBox.setPadding(new Insets(0, 0, 10, 0));

        txtCari = new TextField();
        txtCari.setPromptText("Cari nama atau nomor...");
        txtCari.setPrefWidth(250);
        btnCari = new Button("Cari Data");
        btnCari.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        HBox searchBox = new HBox(10, new Label("Pencarian:"), txtCari, btnCari);

        cmbJenis = new ComboBox<>();
        cmbJenis.getItems().addAll("Semua Jenis", "Sabu", "Ganja", "Ekstasi", "Narkotika Golongan I");
        cmbJenis.setValue("Semua Jenis");

        cmbPengadilan = new ComboBox<>();
        cmbPengadilan.getItems().addAll("Semua Pengadilan", "PN Surabaya", "PN Lainnya");
        cmbPengadilan.setValue("Semua Pengadilan");

        cmbVonis = new ComboBox<>();
        cmbVonis.getItems().addAll("Semua Rentang", "Ringan (< 12 Bulan)", "Sedang (12 - 60 Bulan)", "Berat (> 60 Bulan)");
        cmbVonis.setValue("Semua Rentang");

        btnFilter = new Button("Terapkan Filter");
        btnFilter.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox filterBox = new HBox(10, new Label("Filter Jenis:"), cmbJenis,
                new Label("Pengadilan:"), cmbPengadilan,
                new Label("Vonis:"), cmbVonis, btnFilter);
        filterBox.setPadding(new Insets(0, 0, 15, 0));

        VBox kombinasiHeader = new VBox(10, topBox, searchBox, filterBox);
        mainLayout.setTop(kombinasiHeader);

        table = new TableView<>();

        TableColumn<Putusan, String> colNomor = new TableColumn<>("Nomor Perkara");
        colNomor.setCellValueFactory(new PropertyValueFactory<>("nomorPerkara"));
        colNomor.setPrefWidth(150);

        TableColumn<Putusan, String> colPengadilan = new TableColumn<>("Pengadilan");
        colPengadilan.setCellValueFactory(new PropertyValueFactory<>("pengadilan"));
        colPengadilan.setPrefWidth(120);

        TableColumn<Putusan, String> colTanggal = new TableColumn<>("Tanggal Putusan");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPutusan"));
        colTanggal.setPrefWidth(120);

        TableColumn<Putusan, String> colTerdakwa = new TableColumn<>("Nama Terdakwa");
        colTerdakwa.setCellValueFactory(new PropertyValueFactory<>("namaTerdakwa"));
        colTerdakwa.setPrefWidth(200);

        TableColumn<Putusan, Integer> colUmur = new TableColumn<>("Umur");
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umurTerdakwa"));
        colUmur.setPrefWidth(60);

        TableColumn<Putusan, String> colJenis = new TableColumn<>("Jenis Narkotika");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenisNarkotika"));
        colJenis.setPrefWidth(120);

        TableColumn<Putusan, Double> colBerat = new TableColumn<>("Berat (g)");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("beratBarangBukti"));
        colBerat.setPrefWidth(80);

        TableColumn<Putusan, String> colPasal = new TableColumn<>("Pasal Dilanggar");
        colPasal.setCellValueFactory(new PropertyValueFactory<>("pasalDilanggar"));
        colPasal.setPrefWidth(150);

        TableColumn<Putusan, String> colPeran = new TableColumn<>("Peran Terdakwa");
        colPeran.setCellValueFactory(new PropertyValueFactory<>("peranTerdakwa"));
        colPeran.setPrefWidth(120);

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

        TableColumn<Putusan, String> colHakim = new TableColumn<>("Nama Hakim");
        colHakim.setCellValueFactory(new PropertyValueFactory<>("namaHakim"));
        colHakim.setPrefWidth(150);

        table.getColumns().addAll(
                colNomor, colPengadilan, colTanggal, colTerdakwa, colUmur,
                colJenis, colBerat, colPasal, colPeran, colVonis, colDenda, colHakim
        );
        mainLayout.setCenter(table);

        lblStatus = new Label("Total Data: 0 | Siap Digunakan");
        lblStatus.setStyle("-fx-font-weight: bold; -fx-padding: 10px 0 0 0; -fx-text-fill: #7f8c8d;");
        mainLayout.setBottom(lblStatus);
    }

    public String[] tampilkanFormManualLengkap() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Form Tambah Putusan");
        dialog.setHeaderText("Masukkan detail putusan pengadilan");

        ButtonType btnSimpanType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSimpanType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        TextField txtNomor = new TextField(); txtNomor.setPromptText("123/Pid.Sus/2026");
        TextField txtNama = new TextField(); txtNama.setPromptText("Nama Terdakwa");
        TextField txtJenis = new TextField(); txtJenis.setPromptText("Sabu/Ganja");
        TextField txtBerat = new TextField(); txtBerat.setPromptText("Berat (g)");
        TextField txtVonis = new TextField(); txtVonis.setPromptText("Bulan");
        TextField txtDenda = new TextField(); txtDenda.setPromptText("Nominal (Rp)");

        grid.add(new Label("Nomor Perkara:"), 0, 0); grid.add(txtNomor, 1, 0);
        grid.add(new Label("Nama Terdakwa:"), 0, 1); grid.add(txtNama, 1, 1);
        grid.add(new Label("Jenis Narkotika:"), 0, 2); grid.add(txtJenis, 1, 2);
        grid.add(new Label("Berat Bukti (g):"), 0, 3); grid.add(txtBerat, 1, 3);
        grid.add(new Label("Vonis (Bulan):"), 0, 4); grid.add(txtVonis, 1, 4);
        grid.add(new Label("Denda (Rp):"), 0, 5); grid.add(txtDenda, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSimpanType) {
                return new String[]{
                        txtNomor.getText(), txtNama.getText(), txtJenis.getText(),
                        txtBerat.getText(), txtVonis.getText(), txtDenda.getText()
                };
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    public File tampilkanPilihFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih File CSV atau TXT (Export)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Data Files", "*.csv", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser.showOpenDialog(mainLayout.getScene().getWindow());
    }


    public void perbaruiDataTabel(List<Putusan> daftarList, String pesanStatus) {
        javafx.collections.ObservableList<Putusan> dataTabel = javafx.collections.FXCollections.observableArrayList(daftarList);
        table.setItems(dataTabel);
        lblStatus.setText(pesanStatus);
    }

    public void tampilkanPesanAlert(String tipe, String judul, String pesan) {
        Alert.AlertType alertType = Alert.AlertType.INFORMATION;
        if (tipe.equals("ERROR")) alertType = Alert.AlertType.ERROR;
        else if (tipe.equals("WARNING")) alertType = Alert.AlertType.WARNING;

        Alert alert = new Alert(alertType);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public BorderPane getMainLayout() { return mainLayout; }
    public TableView<Putusan> getTable() { return table; }
    public Button getBtnLoadPdf() { return btnLoadPdf; }
    public Button getBtnTambahManual() { return btnTambahManual; }
    public Button getBtnImport() { return btnImport; }
    public Button getBtnStatistik() { return btnStatistik; }
    public Button getBtnHapus() { return btnHapus; }
    public Button getBtnClear() { return btnClear; }
    public TextField getTxtCari() { return txtCari; }
    public Button getBtnCari() { return btnCari; }
    public Button getBtnExport() { return btnExport; }
    public Label getLblStatus() { return lblStatus; }
    public ComboBox<String> getCmbJenis() { return cmbJenis; }
    public ComboBox<String> getCmbPengadilan() { return cmbPengadilan; }
    public ComboBox<String> getCmbVonis() { return cmbVonis; }
    public Button getBtnFilter() { return btnFilter; }
}