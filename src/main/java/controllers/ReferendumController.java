package controllers;

import data.SessioneDAOImpl;
import data.VotazioneElettoreDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import models.Elettore;
import models.Referendum;
import models.Sessione;

import java.util.Optional;

public class ReferendumController {

    private Elettore E;
    private Sessione S;
    private Referendum R;

    Scene genitore;
    ElettoreController genitoreController;

    @FXML
    private CheckBox favorevoleCheck;

    @FXML
    private CheckBox sfavorevoleCheck;

    @FXML
    private Label quesito;

    @FXML
    private Label titolo;

    @FXML
    private Button votaButton;

    private Alert a = new Alert(
            Alert.AlertType.CONFIRMATION,
            "",
            new ButtonType("Conferma"),
            new ButtonType("Cambia voto")
    );

    /**
     * Formatta il il testo text mandando degli a
     * capo che non spezzano le parole per non sforare
     * la scena
     *
     * @param text il testo da formattare
     * @return il testo formattato
     */
    private static String aCapo (String text) {
        StringBuilder res = new StringBuilder();
        int j = 0;
        int i = 55;
        while ( i<text.length() ) {
            while ( text.charAt(i) != ' ' )
                i++;
            res.append(text.substring(j,i)+"\n");
            j=i+1;
            i += 55;
        }
        res.append(text.substring(j));
        return res.toString();
    }

    void init(Scene G, ElettoreController EC, Sessione S, Elettore E) {
        this.genitore = G;
        this.genitoreController = EC;
        this.S = S;
        this.E = E;
        R = SessioneDAOImpl.getInstance().getReferendum(S.getId());
        quesito.setText(aCapo(R.getQuesito()));
        titolo.setText(S.getTitolo());
    };

    /**
     * Verifica che al massimo una casella sia spuntata
     *
     * @param event la casella cliccata
     */
    @FXML
    void onlyOne(ActionEvent event) {
        String id = ((CheckBox)event.getSource()).getId();
        switch (id) {
            case "favorevoleCheck":
                if (favorevoleCheck.isSelected() && sfavorevoleCheck.isSelected())
                    sfavorevoleCheck.fire();
                break;
            case "sfavorevoleCheck":
                if (favorevoleCheck.isSelected() && sfavorevoleCheck.isSelected())
                    favorevoleCheck.fire();
                break;
        }
    }

    @FXML
    void vota(ActionEvent event) {
        a.setTitle("Conferma");
        a.setHeaderText("Vuoi confermare il seguente voto:");
        a.setContentText(
                favorevoleCheck.isSelected() ? "Sì" :
                        sfavorevoleCheck.isSelected() ? "No" :
                                "Scheda bianca"
        );
        Optional<ButtonType> res = a.showAndWait();
        if (res.get().getText().equals("Conferma"))
             confermaVoto(
                     favorevoleCheck.isSelected() ? Referendum.Risposte.SI :
                             sfavorevoleCheck.isSelected() ? Referendum.Risposte.NO :
                                     Referendum.Risposte.SCHEDA_BIANCA
             );
    }

    void confermaVoto(Referendum.Risposte Ris) {
        switch (Ris) {
            case SI:
                VotazioneElettoreDAOImpl.getInstance().votoElettore(
                        R.addSi(E)
                );
                break;
            case NO:
                VotazioneElettoreDAOImpl.getInstance().votoElettore(
                        R.addSi(E)
                );
                break;
            case SCHEDA_BIANCA:
                VotazioneElettoreDAOImpl.getInstance().votoElettore(
                        R.schedaBianca(E)
                );
        }
        genitoreController.aggiornaSessioni();
        App.getStage().setScene(genitore);
    };

}