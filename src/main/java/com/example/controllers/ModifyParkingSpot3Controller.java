package com.example.controllers;

import com.example.exceptions.IncorrectSectorException;
import com.example.exceptions.ParkinSpotNumberAlreadyExistsException;
import com.example.model.Parking;
import com.example.services.BazaDeDate;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.services.BazaDeDate.*;
import static com.example.services.BazaDeDate.getConnection;

public class ModifyParkingSpot3Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Parking, Integer> numberColumn;

    @FXML
    private TableColumn<Parking, Boolean> occupiedColumn;

    @FXML
    private TableView<Parking> parcare1Table;

    @FXML
    private TableColumn<Parking,String> sectorColumn;

    @FXML
    private Button updateButton;

    @FXML
    private ChoiceBox choiceBoxButton;

    @FXML
    private Button parcare1Button;

    @FXML
    private Button parcare2Button;

    @FXML
    private Button parcare3Button;

    @FXML
    private TextField sectorField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField occupiedField;

    @FXML
    private Label errorLabel;

    private ObservableList<Parking> parkingList;

    private void checkValueOfSector(String value) throws IncorrectSectorException {
        if(!value.equals("C")){
            throw new IncorrectSectorException();
        }
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    @FXML
    void onAddButtonClick(ActionEvent event) throws Exception {
        Connection connection = BazaDeDate.getConnection();
        ArrayList<Integer> myList = getParcare3Number(getConnection());
        try{
            String value1 = sectorField.getText();
            String value2  = numberField.getText();
            String value3 = occupiedField.getText();

            for(int i=0;i<myList.size();i++){
                if(myList.get(i) == Integer.parseInt(value2)){
                    throw new ParkinSpotNumberAlreadyExistsException();
                }
            }
            checkValueOfSector(value1);

            boolean b1 = Boolean.parseBoolean(value3);
            Integer value3Good = boolToInt(b1);
            String value3Better = String.valueOf(value3Good);

            String sql = "INSERT INTO parcare3(sector,number,isoccupied) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,value1);
            ps.setString(2,value2);
            ps.setString(3,value3Better);
            ps.executeUpdate();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        Connection connection = getConnection();
        try{
            String value1 = sectorField.getText();
            String value2  = numberField.getText();
            String value3 = occupiedField.getText();

            boolean b1 = Boolean.parseBoolean(value3);
            Integer value3Good = boolToInt(b1);
            String value3Better = String.valueOf(value3Good);

            String sql = "DELETE FROM parcare3 where sector = ? AND number = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,value1);
            ps.setString(2,value2);
            ps.executeUpdate();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws Exception {
        Connection connection = BazaDeDate.getConnection();
        try{
            String value1 = sectorField.getText();
            String value2  = numberField.getText();
            String value3 = occupiedField.getText();



            boolean b1 = Boolean.parseBoolean(value3);
            Integer value3Good = boolToInt(b1);
            String value3Better = String.valueOf(value3Good);

            String sql = "UPDATE parcare3 set isoccupied = ? WHERE sector = ? and number = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,value3Better);
            ps.setString(2,value1);
            ps.setString(3,value2);
            ps.execute();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        try {
            sectorColumn.setCellValueFactory(new PropertyValueFactory<Parking,String>("sector"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<Parking,Integer>("number"));
            occupiedColumn.setCellValueFactory(new PropertyValueFactory<Parking,Boolean>("isOccupied"));
            parkingList = getDataUsersFromTable3(getConnection());
            parcare1Table.setItems(parkingList);
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onParcare1ButtonClicked(ActionEvent event) {
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
    void onParcare2ButtonClicked(ActionEvent event) {
        try {
            URL url = Paths.get("src/main/resources/com/example/demo1/modify-parkingSpot2-view.fxml").toUri().toURL();
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
    void onParcare3ButtonClicked(ActionEvent event) {
        try {
            URL url = Paths.get("src/main/resources/com/example/demo1/modify-parkingSpot3-view.fxml").toUri().toURL();
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

    int index = -1;

    @FXML
    void getSelected(MouseEvent event) {
        index = parcare1Table.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        sectorField.setText(sectorColumn.getCellData(index).toString());
        numberField.setText(numberColumn.getCellData(index).toString());
        occupiedField.setText(occupiedColumn.getCellData(index).toString());
    }

    public void edit(Connection connection){
        try{
            String value1 = sectorField.getText();
            String value2 = numberField.getText();
            String value3 = occupiedField.getText();

            String sql = "UPDATE parcare1 set sector = ? number = ? isoccupied = ? WHERE sector = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,value1);
            ps.setString(2,value2);
            ps.setString(3,value3);
            ps.execute();
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void onRefreshButtonClicked(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/modify-parkingSpot3-view.fxml").toUri().toURL();
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

    public static ObservableList<Parking> getDataUsersFromTable3(Connection connection) throws Exception {
        ObservableList<Parking> list = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM parcare3";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Parking(rs.getString("sector"),rs.getInt("number"),rs.getBoolean("isoccupied")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
