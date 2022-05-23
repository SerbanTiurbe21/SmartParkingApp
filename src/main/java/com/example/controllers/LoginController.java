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

import static com.example.services.BazaDeDate.getUserRole;
import static com.example.services.BazaDeDate.getConnection;
import static com.example.services.BazaDeDate.isLoggedIn;
import static com.example.services.BazaDeDate.getUserPlateNumber;
import static com.example.services.BazaDeDate.getUsersBalance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class LoginController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    public String getUserName(){
        String name = usernameField.getText();
        return name;
    }

    @FXML
    private Text errorMessage;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink registerHyperLink;

    @FXML
    private TextField usernameField;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        try{
            if(isLoggedIn(getConnection(),
                    usernameField.getText(),
                    passwordField.getText(),
                    getUserRole(getConnection(),
                            usernameField.getText()),
                    getUserPlateNumber(getConnection(),
                            usernameField.getText()))==true && getUserRole(getConnection(),usernameField.getText()).equals("client")){

                URL url = Paths.get("src/main/resources/com/example/smartparkingapp/main-view.fxml").toUri().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                root = loader.load();

                MainViewController mainViewController = loader.getController();
                mainViewController.displayName(usernameField.getText());
                mainViewController.setWelcomeText("Welcome, "+usernameField.getText());
                mainViewController.setDepositLabelText(Integer.toString(getUsersBalance(getConnection(),usernameField.getText())));

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if(isLoggedIn(getConnection(),
                    usernameField.getText(),
                    passwordField.getText(),
                    getUserRole(getConnection(),
                            usernameField.getText()),
                    getUserPlateNumber(getConnection(),
                            usernameField.getText()))==true && getUserRole(getConnection(),usernameField.getText()).equals("admin")){
                URL url = Paths.get("src/main/resources/com/example/smartparkingapp/main-admin-view.fxml").toUri().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                root = loader.load();

                MainViewAdminController mainViewAdminController = loader.getController();
                mainViewAdminController.setUsernameLabel(getUserName());
                mainViewAdminController.setWelcomeText(getUserName());

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception e){
            errorMessage.setText(e.getMessage());
        }
    }

    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/smartparkingapp/register-view.fxml").toUri().toURL();
            root = FXMLLoader.load(url);

            BazaDeDate.deselectFromTable1(getConnection(),usernameField.getText());
            BazaDeDate.deselectFromTable2(getConnection(),usernameField.getText());
            BazaDeDate.deselectFromTable3(getConnection(),usernameField.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            errorMessage.setText(e.getMessage());
        }
    }
}
