package controllers;

import data.GestoreDAOImpl;
import data.PersonaDAOImpl;
import data.SessioneDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Gestore;
import models.Sessione;
import models.SessioneSemplice;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class GestoreController {

    private Gestore G ;

    private Sessione selected=null;

    @FXML
    private Button chiudiButton;

    @FXML
    private Button esitiButton;

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
    private Button logout;

    @FXML
    private Button nuovaSessioneButton;

    @FXML
    private ListView<SessioneSemplice> sessioniAttiveView;

    @FXML
    private ListView<SessioneSemplice> sessioniChiuseView;

    public void init(Gestore G) {
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

    /**
     * Cambia la scena in gestore_nuovaSessione,fxml
     */
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
        selected = SessioneDAOImpl.getInstance().getSessione(SS.getId());
        chiudiButton.setDisable(selected.chiusa());
        esitiButton.setDisable(!selected.chiusa());
        titolo.setText(selected.getTitolo());
        id.setText(String.valueOf(selected.getId()));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = dtf.format(selected.getDataApertura()) + " - " +
                dtf.format(selected.getDataChiusura());
        date.setText(dateString);
        votazione.setText(selected.getTipoVotazione().toString());
        scrutinio.setText(selected.getTipoScrutinio().toString());
    }

    @FXML
    void chiudiClick() {
        SessioneSemplice SS =  sessioniAttiveView.getSelectionModel().getSelectedItem();
        Sessione S = SessioneDAOImpl.getInstance().getSessione(SS.getId());
        GestoreDAOImpl.getInstance().chiudiSessione(S);
        sessioniAttiveView.getItems().remove(SS);
        sessioniChiuseView.getItems().add(SS);
        chiudiButton.setDisable(true);
    }

    @FXML
    void visualizzaEsiti() {
        if (SessioneDAOImpl.getInstance().chiusa(selected)) {
            Scene This = App.getStage().getScene();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/esiti.fxml")
            );
            Stage pS = App.getStage();
            try {
                pS.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pS.setTitle("Esiti");
            pS.setResizable(false);
            ((EsitoController)loader.getController()).init(selected,This,this);
        }
    }

    @FXML
    void logout() {
        GestoreDAOImpl.getInstance().logout(G);
        Stage pStage = App.getStage();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/views/login.fxml")));
            Scene scene = new Scene(root);
            pStage.setScene(scene);
            pStage.setTitle("Login");
            pStage.setResizable(false);
            pStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
