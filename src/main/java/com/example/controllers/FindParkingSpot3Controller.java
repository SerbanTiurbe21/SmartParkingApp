package com.example.controllers;

import com.example.exceptions.NumbersNotWordsException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.model.User.*;

import static com.example.services.BazaDeDate.*;

public class FindParkingSpot3Controller {


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField hours;

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
    @FXML
    void onPayButtonClick(ActionEvent event) throws NumbersNotWordsException {
        validateHoursField(hours.getText());
        int value = Integer.parseInt(hours.getText()) * 7;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String str = formatter.format(date);
        Collection<Integer> collection = new ArrayList<>();
        ArrayList<Integer> altaColectie = new ArrayList<>();
        ArrayList<String> dateTimeCollection = new ArrayList<>();
        try{
            if(checkSufficientFounds(getConnection(),usernameText.getText(),100)==true){
                dateTimeCollection.add(str);
                collection.add(value);
                decreaseUsersBalance(getConnection(),usernameText.getText(),100);
                Integer array[] = collection.toArray(new Integer[collection.size()]);
                for(int i=0;i<array.length;i++){
                    altaColectie.add(array[i]);
                }
                addInPaymentHistory(getConnection(),usernameText.getText(),altaColectie,dateTimeCollection);
            }
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    private void validateHoursField(String text) throws NumbersNotWordsException {
        text = hours.getText();
        Matcher matcher = Pattern.compile("^[0-9]{1,2}$").matcher(text);
        if(!matcher.find()){
            throw new NumbersNotWordsException();
        }
    }

    @FXML
    void onRentButtonClick(ActionEvent event) throws Exception {
        Connection connection = getConnection();
        index = parcareTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        String sql = "UPDATE parcare1 set isoccupied = ?, username = ? where number = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,1);
        ps.setString(2,usernameText.getText());
        ps.setInt(3,numberColumn.getCellData(index));
        ps.executeUpdate();
        payText.setText("Are you sure that you want to rent this spot? If YES, then press PAY!!!");
    }

    @FXML
    void onFavouriteButtonClick(ActionEvent event) throws Exception {
        Connection connection = getConnection();
        String parkingName = parcareCButton.getText();
        String userName = usernameText.getText();
        try{
            updateUsersFavouriteParkingC(connection,userName);
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

}
