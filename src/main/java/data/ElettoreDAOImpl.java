package data;

import models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            while (rs.next()){
                int id = rs.getInt(1);
                String titolo = rs.getString(2);
                LocalDate apertura = rs.getDate(3).toLocalDate();
                LocalDate chiusura = rs.getDate(4).toLocalDate();
                TipoVotazione votazione = TipoVotazione.valueOf(rs.getString(5));
                TipoScrutinio scrutinio = TipoScrutinio.valueOf(rs.getString(6));
                Boolean chiusa = rs.getBoolean(7);
                Gestore g = new Gestore(rs.getString(8));


                res.add(new Sessione(id, titolo, apertura, chiusura, votazione, scrutinio, g.toString() ));
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
    public boolean puoVotare(Elettore E, Sessione S) {
        Objects.requireNonNull(E);
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
