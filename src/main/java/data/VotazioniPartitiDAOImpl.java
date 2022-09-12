package data;

import models.Candidato;
import models.Elettore;
import models.Partito;
import models.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class VotazioniPartitiDAOImpl implements VotazioniPartitiDAO {

    @Override
    public void votaPartito(Map<Partito, Integer> P, Sessione S, Elettore E) {
        Connection db = DbManager.getInstance().getDb();
        String query = "UPDATE \"VotiPartiti\" SET voti=voti+? WHERE sessione=? AND partito=?";
        for (Partito p: P.keySet()) {
            PreparedStatement stmt = null;
            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, P.get(p));
                stmt.setInt(2, S.getId());
                stmt.setInt(3, p.getId());
                stmt.execute();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
