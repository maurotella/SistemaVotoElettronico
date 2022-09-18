package controllers;

import data.GestoreDAOImpl;
import data.ReferendumDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Gestore;
import models.Sessione;

public class NuovoReferendumController {

    private Sessione S;
    private Gestore G;
    private Scene genitore;
    private NuovaSessioneController genitoreCtrl;

    @FXML
    private Button confermaButton;

    @FXML
    private TextArea domanda;

    @FXML
    private Label wordCount;

    @FXML
    private Label titolo;

    void init(Sessione S, Gestore G, Scene genitore, NuovaSessioneController scenaG) {
        titolo.setText(S.getTitolo());
        this.S = S;
        this.G = G;
        this.genitore = genitore;
        genitoreCtrl = scenaG;
        domanda.setOnKeyTyped( X -> {
            wordCount.setText(String.valueOf(domanda.getText().length()));
        });
    }

    @FXML
    void conferma() {
        if (domanda.getText().length()>200) {
            Alert a = new Alert( Alert.AlertType.ERROR );
            a.setContentText(String.format("La domanda deve avere una lunghezza massima di 200 caratteri"));
            a.show();
            return;
        }
        try{
            if (GestoreDAOImpl.getInstance().checkSessione(S)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Confilitto");
                a.setHeaderText(null);
                a.setContentText(
                        String.format(
                                "La sessione '%s' con data di apertura %s è già presente nel database. Inserire una nuova sessione valida",
                                S.getTitolo(), S.getDataApertura()
                        )
                );
                a.show();
                App.getStage().setScene(genitore);
                return;
            }
            GestoreDAOImpl.getInstance().addSessione(G, S);
            ReferendumDAOImpl.getInstance().addReferendum(S, domanda.getText());
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Sessione aggiunta");
            a.setHeaderText(null);
            a.setContentText(String.format("La sessione \"%s\" è stata aggiunta correttamente alle sessioni", S.getTitolo()));
            a.show();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/gestoreMain.fxml")
            );
            App.getStage().setScene(new Scene(loader.load()));
            ((GestoreController)loader.getController()).init(G);

        }catch (Exception e){
            throw new RuntimeException("Errore in confermaClick():\t" + e.getMessage());
        }
    }

}
