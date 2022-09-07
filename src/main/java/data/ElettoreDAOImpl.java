package data;

import models.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.Util;

/**
 * Classe che utilizza il pattern SINGLETON
 */
public class ElettoreDAOImpl implements ElettoreDAO {

    private static ElettoreDAOImpl istance = null;

    private ElettoreDAOImpl() {}

    /**
     * Metodo che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static ElettoreDAOImpl getInstance() {
        if (istance==null)
            istance = new ElettoreDAOImpl();
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
            if (!rs.next())
                return null;
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
                        .dataApertura(Util.dateToLocal(rs.getDate("data_apertura")))
                        .dataChiusura(Util.dateToLocal(rs.getDate("data_chiusura")))
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

    @Override
    public void logout(Elettore E) {
        Auditing.getInstance().registraAzione(
                AzioniAuditing.LOGOUT,
                TipoUtente.ELETTORE,
                E
        );
    }

    @Override
    public boolean puoVotare(Elettore E, SessioneSemplice S) {
        if (!E.dirittoVoto())
            return false;
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"VotiElettori\" WHERE elettore=? AND sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1,E.getCF());
            stmt.setInt(2,S.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
