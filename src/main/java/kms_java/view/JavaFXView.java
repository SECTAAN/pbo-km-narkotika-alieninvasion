package kms_java.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

public class JavaFXView {
    private BorderPane mainLayout;

    public JavaFXView() {
        mainLayout = new BorderPane();
        Label lblTitle = new Label("KMS Putusan Narkotika UMM - GUI Mode");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-padding: 20px;");
        mainLayout.setTop(lblTitle);
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }
}