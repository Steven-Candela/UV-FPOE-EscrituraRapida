module com.example.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.escriturarapida to javafx.fxml;
    exports com.escriturarapida;
    exports com.escriturarapida.controller;
    opens com.escriturarapida.controller to javafx.fxml;
}