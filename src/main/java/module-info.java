module org.openjfx.lab2 {
    requires javafx.controls;
    requires javafx.fxml;

    // FXML needs reflective access to the controller
    opens org.openjfx.lab2 to javafx.fxml;
    exports org.openjfx.lab2;
}
