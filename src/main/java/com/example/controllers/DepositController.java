package com.example.controllers;

import com.example.exceptions.InvalidValueException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

import static com.example.services.BazaDeDate.*;
import static com.example.services.BazaDeDate.getConnection;

public class DepositController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button previousPageButton;

    @FXML
    private Button depositButton;

    @FXML
    private TextField valueField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label errorLabel;

    public String getUserName(){
        return usernameLabel.getText();
    }

    public void setText(String username){
        usernameLabel.setText(username);
    }

    @FXML
    public void onDepositButtonClick(ActionEvent event) throws Exception {
        try{
            String name = usernameLabel.getText();
            Integer value = Integer.parseInt(valueField.getText());
            if(Integer.parseInt(valueField.getText()) < 0){
                throw new InvalidValueException();
            }
            updateUsersBalance(getConnection(),name,value);
            errorLabel.setText("Successful deposit!");
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPreviousPageButton(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/main-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            MainViewController mainViewController = loader.getController();
            mainViewController.setUsernameField(usernameLabel.getText());
            mainViewController.setWelcomeText(usernameLabel.getText());
            mainViewController.setDepositLabelText(Integer.toString(getUsersBalance(getConnection(),usernameLabel .getText())));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
}
