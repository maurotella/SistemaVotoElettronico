package data;

import models.Elettore;
import models.Gestore;
import models.Sessione;
import models.SessioneSemplice;

import java.util.List;

public interface ElettoreDAO {


    /**
     * Dati username e password, effettua l'accesso
     * restituendo l'Elettore corridpondente
     *
     * @param username
     * @param password
     * @return l'Elettore corrispondente se username
     *  e password corretti, null altrimenti
     */
    Elettore login(String username, String password);

    /**
     * Restituisce tutte le sessioni aperte
     *
     * @return una lista di sessioni
     */
     List<Sessione> getSessioni();

    /**
     * Indica se l'elettore E può votare nella sessione S
     *
     * @param E elettore
     * @param S sessione
     * @return true se E può votare in S, false altrimenti
     */
    boolean puoVotare(Elettore E, SessioneSemplice S);

}
