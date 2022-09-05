package data;

import models.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che utilizza il pattern SINGLETON
 */
public class GestoreDAOImpl implements GestoreDAO {

    private static GestoreDAOImpl istance = null;

    private GestoreDAOImpl() {};

    /**
     * Metodo che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static GestoreDAOImpl getIstance() {
        if (istance==null)
            istance = new GestoreDAOImpl();
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
            if (!rs.next())
                return null;
            checkPsw = rs.getString("password");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        Gestore G = null;
        if (BCrypt.checkpw(password,checkPsw)) {
            G = new Gestore(username);
            Auditing.getInstance().registraAzione(
                    AzioniAuditing.LOGIN,
                    TipoUtente.GESTORE,
                    G
            );
        }
        return G;
    }

    /**
     * Trova tutte le sessioni che il Gestore ha creato e che
     * quindi può gestire
     *
     * @param G il gestore
     * @return una lista di sessioni
     */
    @Override
    public List<Sessione> getSessioni(Gestore G) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Sessioni\" WHERE gestore=?";
        ArrayList<Sessione> res = new ArrayList<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, G.getCF());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(SessioneBuilder.newBuilder(rs.getInt("id"))
                    .titolo(rs.getString("titolo"))
                    .dataApertura(dateToLocal(rs.getDate("data_apertura")))
                    .dataChiusura(dateToLocal(rs.getDate("data_chiusura")))
                    .tipoVotazione(TipoVotazione.valueOf(rs.getString("tipo_votazione")))
                    .tipoScrutinio(TipoScrutinio.valueOf(rs.getString("tipo_scrutinio")))
                    .gestore(rs.getString("gestore"))
                    .build()
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return res;
    }

    /**
     * Presa una Date D la converte il LocalDate
     *
     * @param D
     * @return la data in LocalDate
     */
    private static LocalDate dateToLocal(Date D) {
        String[] A = D.toString().split("-");
        int y = Integer.valueOf(A[0]);
        int m = Integer.valueOf(A[1]);
        int d = Integer.valueOf(A[2]);
        return LocalDate.of(y,m,d);
    }

}
