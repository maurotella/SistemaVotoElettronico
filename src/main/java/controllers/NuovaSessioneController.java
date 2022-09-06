

package controllers;


import data.ElettoreDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.*;

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
     * Crea una nuova sessione con le opzioni scelte
     */
    void confermaClick() {

        SessioneSemplice s = new Sessione(
                1,  //che id metto?
                titoloVotazione.getText(),
                dataInizio.getValue(),
                dataFine.getValue(),
                votazioneChoicebox.getValue(),
                scrutinioChoicebox.getValue(),
                G.toString()
        );
    }

    /**
     * Inizializza il Gestore corrente e le choiceBox con i tipi di votazione e scrutini supportati
     */
    @FXML
    void init(Gestore G){
        this.G = G;
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
