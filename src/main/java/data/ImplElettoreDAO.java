package data;

import models.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che utilizza il pattern SINGLETON
 */
public class ImplElettoreDAO implements ElettoreDAO {

    private static ImplElettoreDAO istance = null;

    private ImplElettoreDAO() {}

    /**
     * Metodo che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static ImplElettoreDAO getInstance() {
        if (istance==null)
            istance = new ImplElettoreDAO();
        return istance;
    }

    @Override
    public Elettore login(String username, String password) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Elettori\" WHERE cf=?";
        String checkPsw;
        boolean dv;
        boolean dvd;
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            checkPsw = rs.getString("password");
            dv = rs.getBoolean("diritto_voto");
            dvd = rs.getBoolean("diritto_voto_distanza");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        Elettore E = null;
        if (BCrypt.checkpw(password, checkPsw)) {
            E = new Elettore(username, dv, dvd);
            Auditing.getInstance().registraAzione(
                    AzioniAuditing.LOGIN,
                    TipoUtente.ELETTORE,
                    E
            );
        }
        return E;
    }

    @Override
    public List<Sessione> getSessioni() {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Sessioni\" WHERE chiusa=false";
        ArrayList<Sessione> res = new ArrayList<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
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
