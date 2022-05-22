package com.example.smartparkingapp;

import com.example.services.BazaDeDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.example.services.BazaDeDate;

import static com.example.services.BazaDeDate.connectToTheDatabase;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("SmartParkingApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        connectToTheDatabase();
        launch();
    }
}