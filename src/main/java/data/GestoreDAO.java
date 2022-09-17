package data;

import models.Gestore;
import models.Sessione;

import java.util.List;

public interface GestoreDAO {

    /**
     * Dati username e password ne verifica la correttezza;
     * Registra l'eventuale login (se corretto) tramite auditing
     *
     * @param username
     * @param password
     * @return Il Gestore relativo alle credenziali se corrette,
     *         null altrimenti
     */
    Gestore login(String username, String password);

    /**
     * @param s Sessione da cercare nel DB
     * @return True se s è già presente nellle sessioni nel DB , false altrimenti
     */
    public boolean checkSessione (Sessione s);

    /**
     * Aggiunge una sessione di Votazione al DataBase, se non è presente una altra sessione con
     * lo stesso titolo e la stessa data di apertura
     *
     * @param G Gestore che ha creato la sessione (e che quindi può modificarla)
     * @param s Sessione da aggiugnere al db
     */
    public void addSessione(Gestore G, Sessione s);

    /**
     * Trova tutte le sessioni che il Gestore ha creato e che
     * quindi può gestire
     *
     * @return una lista di sessioni
     */
    List<Sessione> getSessioni();

}
