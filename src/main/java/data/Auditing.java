package data;

import models.TipoUtente;
import models.Utente;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

/**
 * Classe che ha lo scopo di salvare dei log
 * delle azioni effettuare nel database:
 * usa il pattern singleton
 */
public class Auditing {

    private static Auditing istance = null;
    private final HashMap<String,Integer> azioni = new HashMap<String, Integer>();

    /**
     * Costruttore che alla creazione dell'istanza preleva dal db
     * le azioni dei log e i corrispettivi id salvandoli in azioni
     */
    private Auditing() {
        getAzioni();
    }

    /**
     * Preleva le azioni dal db e i relativi id memorizzandoli
     *
     * @return false in presenza di errori, true altrimenti
     */
    public boolean getAzioni() {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Azioni_auditing\"";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                azioni.put(rs.getString("azione"), rs.getInt("id"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Indica l'id dell'azione argomento
     * @param azione
     * @return l'id di azione
     */
    public int getAzioneId(String azione) {
        return azioni.get(azione);
    }
    public static Auditing getInstance() {
        if (istance==null)
            istance = new Auditing();
        return istance;
    }

    public String map() {
        return azioni.toString();
    }

    /**
     * Registra l'azione descritta dagli argomenti nel database
     * inserendo come orario il momento in cui il metodo
     * viene evocato
     *
     * @param azione l'azione eseguita
     * @param chi_ruolo il ruolo di chi ha eseguito l'azione
     * @param chi chi ha eseguito l'azione
     * @return false in presenza di errori, true altrimenti
     */
    public boolean registraAzione (AzioniAuditing azione, TipoUtente chi_ruolo, Utente chi) {
        Connection db = DbManager.getInstance().getDb();
        String query = "INSERT INTO \"Auditing\" VALUES (DEFAULT,?,?,?,?)";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmt.setInt(2,getAzioneId(azione.toString().toUpperCase()));
            stmt.setString(3, chi_ruolo.toString());
            stmt.setString(4, chi.getCF());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
