package com.example.controllers;

import com.example.controllers.MainViewController;
import com.example.model.UserPaymentHistoryClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static com.example.services.BazaDeDate.*;

public class SeePaymentHistoryController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public String getUserName(){
        return usernameLabel.getText();
    }

    @FXML
    private Button previousPageButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ListView<String> paymentHistory;

    @FXML
    private ListView<String> dateList;

    @FXML
    private Label errorLabel;

    public void setUsernameText(String username){
        usernameLabel.setText(username);
    }

    @FXML
    void onPreviousPageButtonClick(ActionEvent event) {
        try{
            URL url = Paths.get("C:\\Users\\Lenovo\\IdeaProjects\\SmartParkingApp\\src\\main\\resources\\com\\example\\smartparkingapp\\main-view.fxml").toUri().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            MainViewController mainViewController = loader.getController();
            mainViewController.setUsernameField(usernameLabel.getText());
            mainViewController.setWelcomeText(usernameLabel.getText());
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
            String cash = getUserCashHistory(getConnection(),getUserName());
            String date = getUserDateHistory(getConnection(),getUserName());
            String[] array = cash.split(" ");
            String[] array2 = date.split(" ");
            for(int i=0;i<array.length;i++){
                if(array[i].equals("null")){
                    array[i] = new String("");
                }
            }
            for(int i=0;i<array2.length;i++){
                if(array2[i].equals("null")){
                    array2[i] = new String("");
                }
            }
            paymentHistory.getItems().addAll(array);
            dateList.getItems().setAll(array2);
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
}