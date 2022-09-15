package controllers;

import data.DbManager;
import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.*;

public class GestioneSessioneController {

    private Gestore G;
    private Scene genitore;
    private NuovaSessioneController ctrl;
    Sessione s;

    @FXML
    private Button confermaButton;

    @FXML
    private TextField domandaField;

    @FXML
    private Button indietroButton;

    @FXML
    private Label nominativo;

    @FXML
    private Label domandaLabel;


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
                App.getStage().setScene(genitore);
                return;
            }
            GestoreDAOImpl.getInstance().addSessione(G, s);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Sessione aggiunta");
            a.setHeaderText(null);
            a.setContentText(String.format("La sessione \"%s\" è stata aggiunta correttamente alle sessioni", s.getTitolo()));
            ctrl.svuota();
            a.show();
            App.getStage().setScene(genitore);

        }catch (Exception e){
            throw new RuntimeException("Errore in confermaClick():\t" + e.getMessage());
        }
    }

    @FXML
    void indietroClick(ActionEvent event) {
        App.getStage().setScene(genitore);
    }

    /**
     * Inizializza la sessione impostando la Label con il nome e cognome del gestore,
     *          memorizza la scena precedente e abilita ulteriori campi se lo scrutinio scelto è REFERENDUM
     * @param G Gestore del sistema
     * @param genitore Scena genitore
     * @param s Impostazioni della sessione
     * @param ctrl Controller della scena precedente
     */
    @FXML
    void init(Gestore G, Scene genitore, Sessione s, NuovaSessioneController ctrl){
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        this.genitore = genitore;
        this.s = s;
        this.ctrl = ctrl;

        if (s.getTipoScrutinio().equals(TipoScrutinio.REFERENDUM)){
            domandaField.setVisible(true);
            domandaLabel.setVisible(true);
        }


    }

}
