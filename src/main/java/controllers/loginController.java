package controllers;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.*;

public class loginController extends Application {

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField psw;
    @FXML
    private TextField user;
    @FXML
    private Label msgLabel;

    @FXML
    void loginClick(ActionEvent event) {
        String userString = user.getText().toUpperCase();
        String pswString = psw.getText();
        if (userString.isEmpty())
            msgLabel.setText("Inserisci un username non vuoto");


//        if (!Util.check(userString)){
//            a.setAlertType(Alert.AlertType.ERROR);
//            a.setContentText("Inserire un Codice Fiscale valido");
//            a.show();
//        }else if(pswString == null || pswString.equals("")){
//            a.setAlertType(Alert.AlertType.ERROR);
//            a.setContentText("Inserire una password valida");
//            a.show();
//        }else if(existInDb(Util.bonify(userString), PassEncTech2.toHexString(PassEncTech2.getSHA(Util.bonify(pswString))))) {
//            logged(new Stage());
//            ((Stage) cf_TextField.getScene().getWindow()).close();
//        }else {
//            a.setAlertType(Alert.AlertType.ERROR);
//            a.setContentText("Login non effettuato " + userString);
//            a.show();
//        }
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
