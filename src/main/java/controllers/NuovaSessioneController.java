

package controllers;


import data.DbManager;
import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller di gestore_nuovaSessione.fmxl
 */
public class NuovaSessioneController {



    /**
     * Gestore corrente
     */
    private Gestore G = null;
    private Scene genitore;
    private SessioneSemplice s;

    @FXML
    private Button avantiButton;

    @FXML
    private DatePicker dataFine;

    @FXML
    private DatePicker dataInizio;

    @FXML
    private Button indietroButton;

    @FXML
    private Label nominativo;

    @FXML
    private ChoiceBox<TipoScrutinio> scrutinioChoicebox;

    @FXML
    private TextField titoloVotazione;

    @FXML
    private ChoiceBox<TipoVotazione> votazioneChoicebox;

    /**
     * Svota tutti i campi di questa scena
     */
    public void svuota(){
        scrutinioChoicebox.setValue(null);
        votazioneChoicebox.setValue(null);
        titoloVotazione.setText("");
        dataFine.setValue(null);
        dataInizio.setValue(null);
    }

    /**
     * Inizializza il Gestore corrente e le choiceBox con i tipi di votazione e scrutini supportati
     */
    @FXML
    void init(Gestore G, Scene genitore){
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        votazioneChoicebox.getItems().addAll(TipoVotazione.REFERENDUM, TipoVotazione.CATEGORICO, TipoVotazione.CATEGORICO_PREFERENZA, TipoVotazione.ORDINALE);
        scrutinioChoicebox.getItems().addAll(TipoScrutinio.REFERENDUM_QUORUM, TipoScrutinio.REFERENDUM, TipoScrutinio.MAGGIORANZA, TipoScrutinio.MAGGIORANZA_ASSOLUTA);
        this.genitore = genitore;

    }


    @FXML
    void indietroClick() {App.getStage().setScene(genitore);}

    @FXML
    void avantiClick(){
        //prima salvo la sessione con i campi che ho inserito, dato che la devo passare alla scena successiva
        Integer idxSessione = null;
        try {
            Connection db = DbManager.getInstance().getDb();
            String query = "SELECT id FROM sve.\"Sessioni\" WHERE ID = (SELECT MAX(ID) FROM sve.\"Sessioni\")";
            PreparedStatement stmt = db.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())  {
                String id = rs.getString(1);
                idxSessione = Integer.parseInt(id) ;
            }
        }catch(Exception e){
            throw new RuntimeException("Errore in confermaClick():\t" + e.getMessage());
        }

        s = new Sessione(
                idxSessione + 1,
                titoloVotazione.getText(),
                dataInizio.getValue(),
                dataFine.getValue(),
                votazioneChoicebox.getValue(),
                scrutinioChoicebox.getValue(),
                G.toString()
        );
       

        try{
            Scene prev = App.getStage().getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestoreSessione.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Gestione Sessione");
            GestioneSessioneController NEW = loader.getController();
            NEW.init(G, prev, (Sessione)s, this);
            App.getStage().show();
        }catch (Exception e){
            throw new RuntimeException("Errore nuovaSessioneClick():\t" + e.getMessage());
        }
    }


}
