package com.example.smartparkingapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private ChoiceBox<String> choiceBoxField;

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField plateNumberField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;
    @FXML
    public void initialize(){
        choiceBoxField.getItems().addAll("Customer","Admin");
    }

    }
