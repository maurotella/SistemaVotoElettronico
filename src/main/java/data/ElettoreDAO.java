package data;

import models.Elettore;
import models.Gestore;
import models.Sessione;

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
    public Elettore login(String username, String password);

    /**
     * Restituisce tutte le sessioni aperte
     *
     * @return una lista di sessioni
     */
    public List<Sessione> getSessioni();

    /**
     * Indica se l'elettore E può votare nella sessione S
     *
     * @param E elettore
     * @param S sessione
     * @return true se E può votare in S, false altrimenti
     */
    public boolean puoVotare(Elettore E, Sessione S);

}
