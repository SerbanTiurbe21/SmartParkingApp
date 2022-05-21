package com.example.controllers;

import com.example.services.BazaDeDate;
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

public class MainViewController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;

    @FXML
    private Button depositButton;

    @FXML
    private Label depositLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button favouritesButton;

    @FXML
    private Button findSpotButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button previousPageButton;

    @FXML
    private Label errorLabel;

    public void displayName(String username){
        usernameLabel.setText(username);
    }

    public void setWelcomeText(String username){
        welcomeText.setText(username);
    }

    public void setDepositLabelText(String value) {
        depositLabel.setText(value);
    }

    public String getUserName(){
        return usernameLabel.getText();
    }

    public void setUsernameField(String username){
        usernameLabel.setText(username);
    }

    @FXML
    public void onDepositButtonClick(ActionEvent event){
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/deposit-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            DepositController depositController = loader.getController();
            depositController.setText(usernameLabel.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onFavouritesButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/favouriteParkingSpot-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();


            FavouriteParkingSpotsController favouriteParkingSpotsController = loader.getController();
            favouriteParkingSpotsController.setUsernameLabel(getUserName());

            int i = 0;
            if(BazaDeDate.getUserParkingAOption(BazaDeDate.getConnection(),getUserName()).equals("true"))
                favouriteParkingSpotsController.adauga(i,"PARKING A");
            if(BazaDeDate.getUserParkingBOption(BazaDeDate.getConnection(),getUserName()).equals("true"))
                favouriteParkingSpotsController.adauga(i,"PARKING B");
            if(BazaDeDate.getUserParkingCOption(BazaDeDate.getConnection(),getUserName()).equals("true"))
                favouriteParkingSpotsController.adauga(i,"PARKING C");

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
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/findParkingSpot-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            FindParkingSpotController findParkingSpotController = loader.getController();
            findParkingSpotController.setUsernameText(getUserName());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPaymentButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/seePaymentHistory-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            SeePaymentHistoryController seePaymentHistoryController = loader.getController();
            seePaymentHistoryController.setUsernameText(usernameLabel.getText());
            //DepositController depositController = loader.getController();
            //depositController.setText(usernameLabel.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPreviousPageButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/login-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            BazaDeDate.deselectFromTable1(BazaDeDate.getConnection(),usernameLabel.getText());
            BazaDeDate.deselectFromTable2(BazaDeDate.getConnection(),usernameLabel.getText());
            BazaDeDate.deselectFromTable3(BazaDeDate.getConnection(),usernameLabel.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
