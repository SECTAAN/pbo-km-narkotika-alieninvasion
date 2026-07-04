package kms_java.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kms_java.controller.KnowledgeController;
import kms_java.view.JavaFXView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        JavaFXView guiView = new JavaFXView();

        KnowledgeController controller = new KnowledgeController(guiView);

        Scene scene = new Scene(guiView.getMainLayout(), 800, 600);
        primaryStage.setTitle("KMS Narkotika UMM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}