module com.example.smartparkingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.smartparkingapp to javafx.fxml;
    exports com.example.smartparkingapp;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
    exports com.example.model;
}