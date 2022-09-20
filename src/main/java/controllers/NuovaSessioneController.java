

package controllers;


import data.DbManager;
import data.PersonaDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

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

    private Integer idxSessione = 0;
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
    private CheckBox votazionePartiti;

    @FXML
    private TextField titoloVotazione;

    @FXML
    private ChoiceBox<TipoVotazione> votazioneChoicebox;

    /**
     * Svota tutti i campi di questa scena
     */
    public void svuota(){
        scrutinioChoicebox.getItems().clear();
        votazioneChoicebox.getItems().clear();
        titoloVotazione.clear();
        dataFine.getEditor().clear();
        dataInizio.getEditor().clear();
    }

    /**
     * Inizializza il Gestore corrente e le choiceBox con i tipi di votazione e scrutini supportati
     */
    @FXML
    void init(Gestore G, Scene genitore){
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        votazioneChoicebox.getItems().addAll(TipoVotazione.values());
        scrutinioChoicebox.getItems().addAll(TipoScrutinio.values());
        votazioneChoicebox.getSelectionModel().selectedItemProperty().addListener(
                (obs, O, N) -> {
                    scrutinioChoicebox.getItems().clear();
                    scrutinioChoicebox.getItems().addAll(TipoScrutinio.values());
                    if (N == TipoVotazione.REFERENDUM) {
                        scrutinioChoicebox.getItems().removeAll(
                                TipoScrutinio.MAGGIORANZA,
                                TipoScrutinio.MAGGIORANZA_ASSOLUTA
                        );
                    } else {
                        scrutinioChoicebox.getItems().removeAll(
                                TipoScrutinio.REFERENDUM,
                                TipoScrutinio.REFERENDUM_QUORUM
                        );
                    }
                }
        );
        this.genitore = genitore;
        dataInizio.setValue(LocalDate.now());
    }

    @FXML
    void indietroClick() {
        App.getStage().setScene(genitore);
        svuota();
    }

    @FXML
    void avantiClick(){
        // controlla dei campi
        if (!checkAll()) {
            Alert a = new Alert( Alert.AlertType.ERROR );
            a.setContentText("Compilare tutti i campi");
            a.show();
            return;
        }
        if (dataInizio.getValue().compareTo(dataFine.getValue())>1) {
            Alert a = new Alert( Alert.AlertType.ERROR );
            a.setContentText("La data di fine non può essere prima di quella d'inizio");
            a.show();
            return;
        }
        //prima salvo la sessione con i campi che ho inserito, dato che la devo passare alla scena successiva

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

        s = SessioneBuilder.newBuilder(idxSessione + 1)
                .titolo(titoloVotazione.getText())
                .dataApertura(dataInizio.getValue())
                .dataChiusura(dataFine.getValue())
                .votazionePartiti(votazionePartiti.isSelected())
                .tipoVotazione(votazioneChoicebox.getValue())
                .tipoScrutinio(scrutinioChoicebox.getValue())
                .gestore(G.toString())
                .build();
        String fileName = "gestoreSessione";
        if (votazioneChoicebox.getValue()==TipoVotazione.REFERENDUM)
            fileName = "domandaReferendum";
        try{
            Scene prev = App.getStage().getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml",fileName)));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Gestione Sessione");
            if (votazioneChoicebox.getValue()==TipoVotazione.REFERENDUM) {
                NuovoReferendumController NEW = loader.getController();
                NEW.init((Sessione) s, G, prev, this);
            } else {
                GestioneSessioneController NEW = loader.getController();
                NEW.init(G, prev, (Sessione) s, this);
            }
            App.getStage().show();
        }catch (Exception e){
            throw new RuntimeException("Errore nuovaSessioneClick():\t" + e.getMessage());
        }
    }

    /**
     * Controlla che tutti i campi siano compilati
     *
     * @return true se tutti i campi sono compilati,
     *         false altrimenti
     */
    boolean checkAll(){
        if (
                votazioneChoicebox.getValue()==null ||
                scrutinioChoicebox.getValue()==null ||
                titoloVotazione.getText()==null ||
                dataInizio.getValue()==null ||
                dataFine.getValue()==null
        )
            return false;
        return true;
    }

}
