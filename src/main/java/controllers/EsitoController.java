package controllers;

import data.SessioneDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.Sessione;
import models.TipoScrutinio;
import models.TipoVotazione;

import java.util.HashMap;

public class EsitoController {

    private Sessione S;
    private Scene genitore;
    private GestoreController genitoreCtrl;
    @FXML
    private HBox graficiContainer;
    @FXML
    private PieChart graficoAstenuti;
    @FXML
    private PieChart graficoEsiti;
    @FXML
    private Button indietroButton;
    @FXML
    private HBox spazioDomanda;
    @FXML
    private Label titolo;

    void init(Sessione S, Scene G, GestoreController GC) {
        this.S = S;
        this.genitore = G;
        this.genitoreCtrl = GC;
        titolo.setText(S.getTitolo());
        if (S.getTipoVotazione() == TipoVotazione.REFERENDUM) {
            setReferendum();
        } else {
            return;
        }
    }

    /**
     * Imposta la scena per visualizzare gli esiti di un referendum
     */
    void setReferendum() {
        Label domanda = new Label(SessioneDAOImpl.getInstance().getReferendum(S.getId()).getQuesito());
        spazioDomanda.getChildren().add(domanda);
        HashMap<Boolean, Integer> esiti = SessioneDAOImpl.getInstance().esitiReferendum(S);
        int si = esiti.get(true);
        int no = esiti.get(false);
        int ast = SessioneDAOImpl.getInstance().nrVotanti(S)-si-no;
        int ele = SessioneDAOImpl.getInstance().nrElettori();
        int vot = SessioneDAOImpl.getInstance().nrVotanti(S);
        graficoEsiti.getData().addAll(
                new PieChart.Data("Sì: "+si, si),
                new PieChart.Data("No: "+no, no),
                new PieChart.Data("Astenuti: "+ast, ast)
        );
        graficoAstenuti.getData().addAll(
                new PieChart.Data("Votanti: "+(si+no+ast), si+no+ast),
                new PieChart.Data("Non votanti: "+(ele-vot), ele-vot)
        );
        graficoEsiti.setTitle(
                S.getTipoScrutinio()==TipoScrutinio.REFERENDUM_QUORUM ?
                        String.format("Esito: %s", si>no ? "SÍ":"NO")
                        :
                        SessioneDAOImpl.getInstance().quorum(S) ?
                                String.format("Esito: %s", si>no ? "SÍ":"NO") : "Esito: quorum NON raggiunto"
        );
    }

    void setVoto() {

    }
    @FXML
    void indietro() {
        App.getStage().setScene(genitore);
    }

    public static void main(String[] args) {
        Sessione S = SessioneDAOImpl.getInstance().getSessione(1);
        System.out.println(SessioneDAOImpl.getInstance().quorum(S));
    }

}
