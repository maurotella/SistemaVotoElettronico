package controllers;

import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import data.SessioneDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.Gestore;
import models.Sessione;
import models.SessioneSemplice;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestoreController {

    private Gestore G ;
    @FXML
    private Label nominativo;

    @FXML
    private Label date;

    @FXML
    private Label id;

    @FXML
    private Label votazione;

    @FXML
    private Label scrutinio;

    @FXML
    private Label titolo;

    @FXML
    private Button nuovaSessioneButton;

    @FXML
    private ListView<SessioneSemplice> sessioniAttiveView;

    @FXML
    private ListView<SessioneSemplice> sessioniChiuseView;

    public void  init(Gestore G) {
        this.G = G;
        this.nominativo.setText(PersonaDAOImpl.getInstance().getNominativo(G.getCF()));
        List<Sessione> sessioni = GestoreDAOImpl.getInstance().getSessioni(G);
        sessioniAttiveView.getItems().addAll(
                sessioni.stream()
                        .filter(x -> !x.chiusa())
                        .map(x -> new SessioneSemplice(x.getId(), x.getTitolo())).toList()
        );
        sessioniChiuseView.getItems().addAll(
                sessioni.stream()
                        .filter(Sessione::chiusa)
                        .map(x -> new SessioneSemplice(x.getId(), x.getTitolo())).toList()
        );

    }

    @FXML
    void nuovaSessioneClick() {
        try{
            Scene prev = App.getStage().getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestore_nuovaSessione.fxml"));
            App.getStage().setScene(new Scene(loader.load()));
            App.getStage().setResizable(false);
            App.getStage().setTitle("Nuova Sessione di Votazione");
            NuovaSessioneController NEW = loader.getController();
            NEW.init(G, prev); //per riempire le choicebox, passo come argomento anche il gestore così ho il riferimento
            App.getStage().show();
        }catch (Exception e){
            throw new RuntimeException("Errore nuovaSessioneClick():\t" + e.getMessage());
        }

    }

    /**
     * Visualizza le info della sessione cliccata
     *
     * @param event l'evento del click
     */
    @FXML
    void infoSessione(MouseEvent event) {
        SessioneSemplice SS = ((ListView<SessioneSemplice>) event.getSource()).getSelectionModel().getSelectedItem();
        if (SS==null)
            return;
        Sessione S = SessioneDAOImpl.getInstance().getSessione(SS.getId());
        titolo.setText(S.getTitolo());
        id.setText(String.valueOf(S.getId()));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = dtf.format(S.getDataApertura()) + " - " +
                dtf.format(S.getDataChiusura());
        date.setText(dateString);
        votazione.setText(S.getTipoVotazione().toString());
        scrutinio.setText(S.getTipoScrutinio().toString());
    }

}
