module com.example.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.escriturarapida to javafx.fxml;
    exports com.escriturarapida;
    exports com.escriturarapida.controllers;
    opens com.escriturarapida.controllers to javafx.fxml;
}