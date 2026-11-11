module org.openjfx.lab3 {
    requires javafx.controls;
    requires javafx.fxml;

    // FXML needs reflective access to the controller
    opens org.openjfx.lab3 to javafx.fxml;
    exports org.openjfx.lab3;
}
