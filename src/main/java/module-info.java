module com.example.smartparkingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.smartparkingapp to javafx.fxml;
    exports com.example.smartparkingapp;
}