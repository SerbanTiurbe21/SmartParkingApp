package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class MainViewAdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label priceHourLabel;

    @FXML
    private Label freeSpotsLabel;

    @FXML
    private Button parcareAButton;

    @FXML
    private Button parcareBButton;

    @FXML
    private Button parcareCButton;

    @FXML
    private Button modifySpotButton;

    @FXML
    private Button paymentHistoryButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label welcomeText;

    @FXML
    private Button previousPageButton;

    @FXML
    private Label errorLabel;

    public void setUsernameLabel(String username){
        usernameLabel.setText(username);
    }

    public void setWelcomeText(String username){
        welcomeText.setText(username);
    }

    @FXML
    void onModifySpotButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/modify-parkingSpot1-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPaymentHistoryButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/seePaymentHistoryAll-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            //SeePaymentHistoryAllController seePaymentHistoryAllController = loader.getController();
            //seePaymentHistoryAllController.setUsernameLabel(usernameLabel.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPreviousPageButton(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/login-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onFindButtonSpotClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/findParkingSpot-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
}
