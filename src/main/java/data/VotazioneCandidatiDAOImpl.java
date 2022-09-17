package data;

import models.Candidato;
import models.Elettore;
import models.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class VotazioneCandidatiDAOImpl implements VotazioneCandidatiDAO {

    private static VotazioneCandidatiDAOImpl istance;

    private VotazioneCandidatiDAOImpl() {};

    public static VotazioneCandidatiDAOImpl getInstance() {
        if (istance==null)
            istance = new VotazioneCandidatiDAOImpl();
        return istance;
    }

    @Override
    public void votaCandidato(Map<Candidato, Integer> C, Sessione S, Elettore E) {
        Connection db = DbManager.getInstance().getDb();
        String query = "UPDATE \"VotiCandidati\" SET voti=voti+? WHERE sessione=? AND candidato=?";
        for (Candidato c: C.keySet()) {
            PreparedStatement stmt = null;
            try {
                stmt = db.prepareStatement(query);
                stmt.setInt(1, C.get(c));
                stmt.setInt(2, S.getId());
                stmt.setInt(3, c.getId());
                stmt.execute();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addVotazioneCandidato(Sessione S, Candidato C) {
        Connection db = DbManager.getInstance().getDb();
        String query = "INSERT INTO \"VotiCandidati\" VALUES (?,?,?)";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,C.getId());
            stmt.setInt(2,S.getId());
            stmt.setInt(3,0);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void votaCandidato(Candidato C, Sessione S, Elettore E) {
        HashMap<Candidato, Integer> M = new HashMap<>(1);
        M.put(C,1);
        votaCandidato(M, S, E);
    }

}
