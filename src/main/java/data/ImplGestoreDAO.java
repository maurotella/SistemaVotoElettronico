package data;

import models.Gestore;
import models.Sessione;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe che utilizza il pattern SINGLETON
 */
public class ImplGestoreDAO implements GestoreDAO {

    private static ImplGestoreDAO istance = null;

    private ImplGestoreDAO() {};

    /**
     * Classe che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static ImplGestoreDAO getIstance() {
        if (istance==null)
            istance = new ImplGestoreDAO();
        return istance;
    };

    @Override
    public Gestore login(String username, String password) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Gestori\" WHERE cf=?";
        String checkPsw = "";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            checkPsw = rs.getString("password");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return BCrypt.checkpw(password,checkPsw)? new Gestore(username):null;
    }

    @Override
    public List<Sessione> getSessioni(Gestore G) {
        return null;
    }
}
