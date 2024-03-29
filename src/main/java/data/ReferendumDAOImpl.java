package data;

import models.Elettore;
import models.Sessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReferendumDAOImpl implements ReferendumDAO {

    private static ReferendumDAOImpl istance;

    private ReferendumDAOImpl() {};

    public static ReferendumDAOImpl getInstance() {
        if (istance==null)
            istance = new ReferendumDAOImpl();
        return istance;
    }

    @Override
    public void votaSi(Sessione S, Elettore E) {
        Connection db = DbManager.getInstance().getDb();
        String query = "UPDATE \"Referendum\" SET si=si+1 WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void votaNo(Sessione S, Elettore E) {
        Connection db = DbManager.getInstance().getDb();
        String query = "UPDATE \"Referendum\" SET no=no+1 WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addReferendum(Sessione S, String domanda) {
        Connection db = DbManager.getInstance().getDb();
        String query = "INSERT INTO \"Referendum\" VALUES (?,?,0,0)";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            stmt.setString(2, domanda);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
