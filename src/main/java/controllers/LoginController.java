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

    void initialize() {
        System.out.println("Initialize");
        user.clear();
        psw.clear();
    }
    @FXML
    void loginClick() {
        String userString = user.getText().toUpperCase();
        String pswString = psw.getText();
        a.setHeaderText(null);
        a.setTitle("Errore");
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
                U = GestoreDAOImpl.getInstance().login(userString,pswString);
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
                file = "gestoreMain";
            }
            Scene G = App.getStage().getScene();;
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(String.format("/views/%s.fxml",file))
                );
                Stage pS = App.getStage();
                pS.setScene(new Scene(loader.load()));
                pS.setTitle(titolo);
                pS.setResizable(false);
                if (U.tipoUtente()==TipoUtente.GESTORE) {
                    GestoreController GC = loader.getController();
                    GC.init((Gestore)U);
                } else {
                    ElettoreController EC = loader.getController();
                    //noinspection ConstantConditions
                    EC.init((Elettore) U, G, this);
                }
                pS.show();
            } catch (IOException e) {
                throw new RuntimeException("Errore in LoginController:\t" + e);
            }
        }
    }
}