module com.example.squareboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.callum.squareboard to javafx.fxml;
    exports com.callum.squareboard;
}