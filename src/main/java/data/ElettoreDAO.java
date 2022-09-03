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

}
