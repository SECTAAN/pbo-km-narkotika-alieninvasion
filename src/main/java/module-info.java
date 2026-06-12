module alieninvasion.pbokmnarkotikaalieninvasion {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens kms_java.view to javafx.fxml;
    opens kms_java.controller to javafx.fxml;

    exports kms_java.app;
    exports kms_java.controller;
    exports kms_java.view;
    exports kms_java.model;
}