package com.example.controllers;

import com.example.model.Parking;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import static com.example.services.BazaDeDate.*;

public class FavouriteParkingSpotsController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button previousPageButton;

    @FXML
    private TableView<String> favouriteTable;

    @FXML
    private TableColumn<String, String> parkingColumn;

    @FXML
    private ListView<String> parkingList;

    private ObservableList<String> parkingList2;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label errorLabel;

    public String getUserName(){
        return usernameLabel.getText();
    }

    public void setUsernameText(String username){
        usernameLabel.setText(username);
    }

    public void adauga(int index,String nume){
        parkingList.getItems().add(index++,nume);
    }

    @FXML
    void onPreviousPageButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/main-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            MainViewController mainViewController = loader.getController();
            mainViewController.setUsernameField(getUserName());
            mainViewController.setWelcomeText(getUserName());
            mainViewController.setDepositLabelText(Integer.toString(getUsersBalance(getConnection(),usernameLabel.getText())));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //parkingList.getItems().add(0,getUserName());
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
}