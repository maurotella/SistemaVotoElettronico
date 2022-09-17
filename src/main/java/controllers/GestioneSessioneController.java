package controllers;

import data.GestoreDAOImpl;
import data.PartitoDAOImpl;
import data.PersonaDAOImpl;
import data.VotazioneCandidatiDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.*;

import java.util.*;

public class GestioneSessioneController {

    private Gestore G;
    private Scene genitore;
    private NuovaSessioneController ctrl;
    private Sessione s;
    // Mappa che associa i candidati con il loro checkBox id (tramite indice)
    private HashMap<Integer,Candidato> candidatiIdCB;
    private HashMap<Partito, ArrayList<Candidato>> lista;
    private List<Candidato> selezionati;
    @FXML
    private ListView<PartitoStringato> elencoPartiti;

    @FXML
    private VBox elencoCandidati;

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
                a.setContentText(
                        String.format(
                                "La sessione '%s' con data di apertura %s è già presente nel database. Inserire una nuova sessione valida",
                                s.getTitolo(), s.getDataApertura()
                        )
                );
                a.show();
                App.getStage().setScene(genitore);
                return;
            }
            GestoreDAOImpl.getInstance().addSessione(G, s);
            if (!s.isVotazionePartiti()) {
                selezionati.forEach(
                        candidato -> {
                            VotazioneCandidatiDAOImpl.getInstance().addVotazioneCandidato(s,candidato);
                        }
                );
            }
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Sessione aggiunta");
            a.setHeaderText(null);
            a.setContentText(String.format("La sessione \"%s\" è stata aggiunta correttamente alle sessioni", s.getTitolo()));
            ctrl.svuota();
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
        lista = PartitoDAOImpl.getInstance().getListaPartiti();
        int len = lista.values().stream().flatMap(List::stream).toArray().length;
        candidatiIdCB = new HashMap<>();
        selezionati = new LinkedList<>();
        inserisciPartiti();
    }

    /**
     * Inserisci nella ListView tutti i partiti
     */
    void inserisciPartiti() {
        lista.keySet().forEach(
                partito -> elencoPartiti.getItems().add(new PartitoStringato(partito))
        );
    }

    /**
     * Inserisce le checkBox candidati in base al partito cliccato
     *
     * @param P il partito selezionato
     */
    void inserisciCandidati(Partito P) {
        int i = 0;
        HBox hbox = null;
        for (Candidato candidato : lista.get(P)) {
            if (i%2==0) {
                hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                elencoCandidati.getChildren().add(hbox);
            }
            CheckBox cb = new CheckBox(
                    new VotoOrdinaleController.CandidatoStringato(candidato).toString()
            );
            if (selezionati.contains(candidatiIdCB.get(candidato.getId())))
                cb.setSelected(true);
            cb.setId(String.valueOf(candidato.getId()));
            cb.setPadding(new Insets(0,12,0,0));
            cb.setFont(new Font(15));
            candidatiIdCB.put(candidato.getId(), candidato);
            cb.setOnAction( action -> {
                CheckBox sel = (CheckBox) action.getSource();
                if (sel.isSelected())
                    selezionati.add(candidatiIdCB.get(Integer.parseInt(sel.getId())));
                else
                    selezionati.remove(candidatiIdCB.get(Integer.parseInt(sel.getId())));
            }
            );
            hbox.getChildren().add(cb);
            i++;
        }
    }

    void pulisciCandidati() {
        elencoCandidati.getChildren().clear();
    }

    /**
     * Azione quando viene cliccato un partito nella ListView
     */
    @FXML
    void selezionaPartito() {
        PartitoStringato selected = elencoPartiti.getSelectionModel().getSelectedItem();
        pulisciCandidati();
        inserisciCandidati(selected.getPartito());
    }

    public static class PartitoStringato {

        private final Partito partito;
        public PartitoStringato(Partito partito) {
            this.partito = partito;
        }

        public Partito getPartito() {
            return partito;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PartitoStringato that = (PartitoStringato) o;
            return partito.equals(that.partito);
        }

        @Override
        public int hashCode() {
            return Objects.hash(partito);
        }

        public String toString() {
            return partito.getNome();
        }

    }

}
