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
public class GestoreDAOImpl implements GestoreDAO {

    private static GestoreDAOImpl istance = null;

    private GestoreDAOImpl() {};

    /**
     * Metodo che implemente il pattern singleton
     *
     * @return l'unica istanza
     */
    public static GestoreDAOImpl getInstance() {
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

    public boolean checkSessione (Sessione s) {
        try {
            Connection db = DbManager.getInstance().getDb();
            String query = "SELECT * FROM \"Sessioni\" ";
            query += String.format("WHERE titolo = '%s' AND data_apertura between '%s' and '%s'", s.getTitolo(), s.getDataApertura(), s.getDataApertura());
            PreparedStatement ps = db.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Errore in checkSessione: \t" + e.getMessage());
        }
    }

    public void addSessione(Gestore G, Sessione s){
        try {
            Connection db = DbManager.getInstance().getDb();
            String query = "INSERT INTO \"Sessioni\" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) VALUES (";
            query += String.format("%d, '%s', '%s', '%s', '%s', '%s', %s, '%s')", s.getId(), s.getTitolo(), s.getDataApertura().toString(), s.getDataChiusura().toString(), s.getTipoVotazione(), s.getTipoScrutinio(), s.chiusa(), G.getCF());
            Statement stmt = db.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            throw new RuntimeException("Errore in addSessione:\t" + e.getMessage());
        }
    }

    @Override
    public List<Sessione> getSessioni() {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM sve.\"Sessioni\" WHERE chiusa=false";
        ArrayList<Sessione> res = new ArrayList<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String titolo = rs.getString(2);
                LocalDate apertura = rs.getDate(2).toLocalDate();;
                LocalDate chiusura = rs.getDate(3).toLocalDate();;
                TipoVotazione votazione = TipoVotazione.valueOf(rs.getString(4));
                TipoScrutinio scrutinio = TipoScrutinio.valueOf(rs.getString(5));
                Boolean chiusa = rs.getBoolean(6);
                Gestore g = new Gestore(rs.getString(7));
                Boolean votazioniPartiti = rs.getBoolean(9);
                //System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", titolo, apertura.toString(), chiusura.toString(), votazione, scrutinio, chiusa, g.toString());
                res.add(new Sessione(id, titolo, apertura, chiusura, votazione,votazioniPartiti, scrutinio, g.toString() ));

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
