package com.example.controllers;

import com.example.model.Parking;
import com.example.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.example.model.User.*;

import static com.example.services.BazaDeDate.*;

public class FindParkingSpot3Controller {


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<Parking, Integer> numberColumn;

    @FXML
    private TableColumn<Parking, Boolean> occupiedColumn;

    @FXML
    private TableColumn<Parking, String> sectorColumn;

    @FXML
    private TableView<Parking> parcareTable;

    @FXML
    private Label freeSpotsLabel;

    @FXML
    private Button parcareAButton;

    @FXML
    private Button parcareBButton;

    @FXML
    private Button parcareCButton;

    @FXML
    private Button backToDepsosit;

    @FXML
    private Label errorLabel;

    @FXML
    private Button previousPageButton;

    @FXML
    private Label usernameText;

    @FXML
    private Button payButton;

    @FXML
    private Button rentButton;

    @FXML
    private TextField sectorField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField occupiedField;

    @FXML
    private Text payText;

    public String getUserName(){
        return usernameText.getText();
    }

    public void setUsernameText(String username){
        usernameText.setText(username);
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
            mainViewController.setDepositLabelText(Integer.toString(getUsersBalance(getConnection(),usernameText.getText())));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }


    @FXML
    void onBackToDepsositClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/deposit-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            //DepositController depositController = loader.getController();
            //depositController.setText(usernameText.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    private ObservableList<Parking> parkingList;

    @FXML
    void onParcareAButtonClick(ActionEvent event) {
        try {
            URL url = Paths.get("src/main/resources/com/example/demo1/findParkingSpot-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            FindParkingSpotController findParkingSpotController = loader.getController();
            findParkingSpotController.setUsernameText(usernameText.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onParcareBButtonClick(ActionEvent event) {
        try {
            URL url = Paths.get("src/main/resources/com/example/demo1/findParkingSpot2-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            FindParkingSpot2Controller findParkingSpot2Controller = loader.getController();
            findParkingSpot2Controller.setUsernameText(usernameText.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onParcareCButtonClick(ActionEvent event) {
        try {
            URL url = Paths.get("src/main/resources/com/example/demo1/findParkingSpot3-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            FindParkingSpot3Controller findParkingSpot3Controller = loader.getController();
            findParkingSpot3Controller.setUsernameText(usernameText.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    int index = -1;

    @FXML
    public void getSelected(MouseEvent event) {
        index = parcareTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        sectorField.setText(sectorColumn.getCellData(index));
        numberField.setText(numberColumn.getCellData(index).toString());
        occupiedField.setText(occupiedColumn.getCellData(index).toString());
    }

    @FXML
    public void initialize() throws Exception {
        try{
            sectorColumn.setCellValueFactory(new PropertyValueFactory<Parking,String>("sector"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<Parking,Integer>("number"));
            occupiedColumn.setCellValueFactory(new PropertyValueFactory<Parking,Boolean>("isOccupied"));
            parkingList = getFreeSpotsTableC(getConnection());
            parcareTable.setItems(parkingList);
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

}
