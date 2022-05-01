package com.example.controllers;

import com.example.services.BazaDeDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.example.services.BazaDeDate.insertValues;
import static com.example.services.BazaDeDate.getConnection;

public class RegisterController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox choiceBoxField;

    @FXML
    public void initialize(){
        choiceBoxField.getItems().addAll("CUSTOMER","ADMIN");
    }

    @FXML
    private Text errorMessageText;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField plateNumberField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    protected void onRegisterButtonClick() throws Exception{
        try{
            insertValues(getConnection(),
                    usernameField.getText(),
                    passwordField.getText(),
                    (String) choiceBoxField.getValue(),
                    0,
                    plateNumberField.getText());
        }catch (Exception e){
            errorMessageText.setText(e.getMessage());
        }
    }

}

