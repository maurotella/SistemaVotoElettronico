package controllers;

import data.DbManager;
import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestioneSessioneController {

    private Gestore G;
    private Scene genitore;
    Sessione s;

    @FXML
    private Button confermaButton;

    @FXML
    private TextField domandaField;

    @FXML
    private Button indietroButton;

    @FXML
    private Label nominativo;

    /**
     * Aggiunge la sessione s (ricevuta dalla scena precedente) alla lista delle sessioni nel db.
     * Se è già presente una sessione con lo stesso titolo e la stessa data di apertura mostra un messaggio di errore
     */
    @FXML
    void confermaClick() {
        try{
            if (GestoreDAOImpl.getInstance().checkSessione(s)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Confilitto");
                a.setHeaderText(null);
                a.setContentText(String.format("La sessione '%s' con data di apertura %s è già presente nel database. Inserire una nuova sessione valida", s.getTitolo(), s.getDataApertura()));
                a.show();
                return;
            }
            GestoreDAOImpl.getInstance().addSessione(G, s);
        }catch (Exception e){
            throw new RuntimeException("Errore in confermaClick():\t" + e.getMessage());
        }
    }

    @FXML
    void indietroClick(ActionEvent event) {
        App.getStage().setScene(genitore);
    }

    @FXML
    void init(Gestore G, Scene genitore, Sessione s){
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        this.genitore = genitore;
        this.s = s;
        System.out.println(s.toString());
    }

}
