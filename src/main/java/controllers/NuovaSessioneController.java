

package controllers;


import data.DbManager;
import data.ElettoreDAOImpl;
import data.GestoreDAOImpl;
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

    // String[] votazione = {"Maggioranza", "Maggioranza assoluta", "Referenzum SENZA quorum", "Referendum CON quorum"};
//    private TipoVotazione[] tipiVotazione
//    private String[] scrutinio = {"Voto ordinale", "Voto Categorico", "Voto categorico con preferenze", "Referendum"};

    /**
     * Gestore corrente
     */
    private Gestore G = null;

    @FXML
    private Button confermaButton;

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

    @FXML

    /**
     * Crea una nuova sessione con le opzioni scelte.
     * L'id per la nuova sessione è pari al massimo degli id presenti nel db + 1
     */
    void confermaClick() {
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


        SessioneSemplice s = new Sessione(
                idxSessione + 1,
                titoloVotazione.getText(),
                dataInizio.getValue(),
                dataFine.getValue(),
                votazioneChoicebox.getValue(),
                scrutinioChoicebox.getValue(),
                G.toString()
        );
        GestoreDAOImpl.getInstance().addSessione(this.G, (Sessione)s);


//ora devo mettere la sessione all' interno dell'elemento LISTVIEW in gestoreMain.fxml

    }

    /**
     * Inizializza il Gestore corrente e le choiceBox con i tipi di votazione e scrutini supportati
     */
    @FXML
    void init(Gestore G){
        this.G = G;
        //System.out.println("Questo è this.G: " + G.getCF());
        votazioneChoicebox.getItems().addAll(TipoVotazione.REFERENDUM, TipoVotazione.CATEGORICO, TipoVotazione.CATEGORICO_PREFERENZA, TipoVotazione.ORDINALE);
        scrutinioChoicebox.getItems().addAll(TipoScrutinio.REFERENDUM_QUORUM, TipoScrutinio.REFERENDUM, TipoScrutinio.MAGGIORANZA, TipoScrutinio.MAGGIORANZA_ASSOLUTA);
    }



    @FXML
    void indietroClick() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestoreMain.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Gestore");
            App.getStage().show();
        }catch (Exception e){
            throw new RuntimeException("Errore indietroClick()" + e.getMessage());
        }
    }

}
