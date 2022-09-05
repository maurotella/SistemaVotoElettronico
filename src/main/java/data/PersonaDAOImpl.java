package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAOImpl implements PersonaDAO {

    private static PersonaDAOImpl instance = null;

    private PersonaDAOImpl() {};

    public static PersonaDAOImpl getInstance() {
        if (instance==null)
            instance = new PersonaDAOImpl();
        return instance;
    }

    @Override
    public String getNominativo(String CF) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT nome,cognome FROM \"Persone\" WHERE cf=?";
        String res = "";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1,CF);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            res = String.format("%s %s", rs.getString("nome"), rs.getString("cognome"));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

}
