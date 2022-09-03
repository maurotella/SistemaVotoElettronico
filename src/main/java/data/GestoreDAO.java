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
    public Gestore login(String username, String password);

    /**
     * Restituisce tutte le sessioni che il
     * gestore G ha creato
     *
     * @return una lista di sessioni
     */
    public List<Sessione> getSessioni(Gestore G);

}
