package controllers;

import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import models.TipoUtente;
import models.Utente;

import java.io.IOException;

public class LoginController {

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
        } else {
            // Login
            Utente U = ElettoreDAOImpl.getInstance().login(userString,pswString);
            if (U==null) {
                U = GestoreDAOImpl.getIstance().login(userString,pswString);
                if (U==null) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Credenziali errate");
                    a.show();
                    return;
                }
            }
            String titolo;
            String file;
            if (U.tipoUtente()==TipoUtente.ELETTORE) {
                titolo = "Elettore";
                file = "elettore";
            } else {
                titolo = "Gestore";
                file = "gestore";
            }
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(String.format("/views/%s.fxml",file))
                );
                App.getStage().setScene(new Scene(loader.load()));
                App.getStage().setTitle(titolo);
                App.getStage().setResizable(false);
                if (U.tipoUtente()==TipoUtente.GESTORE) {
                    GestoreController GC = loader.getController();
                    GC.init(PersonaDAOImpl.getInstance().getNominativo(U.getCF()));
                } else {
                    ElettoreController EC = loader.getController();
                    EC.init(PersonaDAOImpl.getInstance().getNominativo(U.getCF()));
                }
                App.getStage().show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}