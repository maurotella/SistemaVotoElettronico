package data;

import models.TipoUtente;
import models.VotazioneElettore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class VotazioneElettoreDAOImpl implements VotazioneElettoreDAO {

    private static VotazioneElettoreDAOImpl istance;

    private VotazioneElettoreDAOImpl() {};

    public static VotazioneElettoreDAOImpl getInstance() {
        if (istance==null)
            istance = new VotazioneElettoreDAOImpl();
        return istance;
    }

    @Override
    public void votoElettore(VotazioneElettore VE) {
        Connection db = DbManager.getInstance().getDb();
        String query = "INSERT INTO \"VotiElettori\" VALUES (?,?,?)";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, VE.getElettore().getCF());
            stmt.setInt(2, VE.getSessione());
            stmt.setTimestamp(3, Timestamp.valueOf(VE.getOrario()));
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Auditing.getInstance().registraAzione(
                AzioniAuditing.VOTAZIONE,
                TipoUtente.ELETTORE,
                VE.getElettore()
        );
    }
}
