package com.example.controllers;

import com.example.model.UserPaymentHistoryClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

import static com.example.services.BazaDeDate.getConnection;
import static com.example.services.BazaDeDate.getUserPaymentHistory;

public class SeePaymentHistoryControllerAll {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setUsernameLabel(String name) {
        usernameLabel.setText(name);
    }

    public String getUsernameLabel(){
        return usernameLabel.getText();
    }

    @FXML
    private TableColumn<UserPaymentHistoryClass, String> dateColumn;

    @FXML
    private TableColumn<UserPaymentHistoryClass, String> paymentColumn;

    @FXML
    private TableView<UserPaymentHistoryClass> userPaymentTable;

    @FXML
    private TableColumn<UserPaymentHistoryClass, String> usernameColumn;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField usernameSearchField;

    @FXML
    private Button previousPage;

    private ObservableList<UserPaymentHistoryClass> paymentHistory;


    @FXML
    public void initialize() throws Exception {
        try{
            usernameColumn.setCellValueFactory(new PropertyValueFactory<UserPaymentHistoryClass,String>("username"));
            paymentColumn.setCellValueFactory(new PropertyValueFactory<UserPaymentHistoryClass,String>("payment"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<UserPaymentHistoryClass,String>("date"));
            paymentHistory = getUserPaymentHistory(getConnection());
            userPaymentTable.setItems(paymentHistory);
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void onPreviousPageButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("src/main/resources/com/example/demo1/main-admin-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            MainViewAdminController mainViewController = loader.getController();
            mainViewController.setUsernameField(usernameLabel.getText());
            mainViewController.setWelcomeText(usernameLabel.getText());
            //mainViewController.setDepositLabelText(Integer.toString(getUsersBalance(getConnection(),usernameLabel.getText())));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
}
