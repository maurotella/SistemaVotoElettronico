package data;

import models.Gestore;
import models.Sessione;
import java.util.List;

public interface GestoreDAO {

    /**
     * Dati username e password, effettua l'accesso
     * restituendo il Gestore corridpondente
     *
     * @param username
     * @param password
     * @return il Gestore corrispondente se username
     *  e password corretti, null altrimenti
     */
    public Gestore login(String username, String password);

    /**
     * Restituisce tutte le sessioni che il
     * gestore G ha creato
     *
     * @return una lista di sessioni
     */
    public List<Sessione> getSessioni(Gestore G);

}
