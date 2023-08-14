module bergin {
    requires javafx.controls;
    requires javafx.fxml;


    opens bergin.c482task1 to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    exports bergin.c482task1;
    exports controller;
    exports model;

}