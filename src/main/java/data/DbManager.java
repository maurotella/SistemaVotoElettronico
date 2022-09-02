package data;

import java.sql.*;

/**
 * Classe che implementa il pattern singleton
 * il cui scopo è connettersi al db
 */
public class DbManager {

    private static DbManager istance = null;
    private Connection db = null;

    public DbManager() {}

    public static DbManager getInstance() {
        if (istance==null)
            istance = new DbManager();
        return istance;
    }

    /**
     * Effettua una connessione permanente al db
     *
     * @return false in presenza di errori
     *         di connessione, true altrimenti
     */
    public boolean connect() {
        if (db==null) {
            String url = "jdbc:postgresql://localhost:5432/sveData";
            String user = "postgres";
            String password = "ciaociao";
            try {
                db = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = db.prepareStatement("SET search_path TO sve;");
                stmt.execute();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Chiude la connessione con il db
     */
    public void closeConnection() {
        try {
            db.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getDb() {
        if (db==null)
            connect();
        return db;
    }

    @Override
    public String toString() {
        return "DbManager{\n" +
                "    db=" + db + "\n" +
                '}';
    }
}
