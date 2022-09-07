package data;

import javafx.scene.control.Alert;
import models.Gestore;
import models.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     * Restituisce tutte le sessioni che il
     * gestore G ha creato
     *
     * @return una lista di sessioni
     */
    List<Sessione> getSessioni(Gestore G);

    }
