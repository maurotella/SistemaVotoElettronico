package controllers;

import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Elettore;
import models.Gestore;

public class LoginController extends Application {

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField psw;
    @FXML
    private TextField user;
    @FXML
    private Label msgLabel;

    Alert a = new Alert(Alert.AlertType.NONE);

    @FXML
    void loginClick() {
        String userString = user.getText().toUpperCase();
        String pswString = psw.getText();
        // Controllo campi non vuoti
        if (userString.isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Inserire il codice fiscale");
            a.show();
        } else if (pswString.isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Inserire una password");
            a.show();
        }
        // Login
        Elettore E = ElettoreDAOImpl.getInstance().login(userString,pswString);
        if (E==null) {
            Gestore G = GestoreDAOImpl.getIstance().login(userString,pswString);
            if (G==null) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Credenziali errate");
                a.show();
            }
        }

    }

    @Override
    public void start(Stage primaryStage) {

    }
}
