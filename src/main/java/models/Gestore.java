package models;

import data.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gestore extends Utente {

    /**
     * Crea il gestore con codice fiscale CF
     *
     * @param CF codice fiscale
     */
    public Gestore (String CF){
        super(CF);
    }

    /**
     * @return CF di this
     */

    @Override
    public TipoUtente tipoUtente() {
        return TipoUtente.GESTORE;
    }

    /**
     *
     * @return CF del Gestore (this)
     */
    public String getCF (){return  super.getCF();}

    public String toString() {
        return super.toString()
                .replaceFirst("Utente", "Gestore");
    }


    /**
     * Ha senso metterlo qua? Perchè è anche in GestoreDAOImpl e ElettoreDAOimpl??
     * @return
     */
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
                //System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", titolo, apertura.toString(), chiusura.toString(), votazione, scrutinio, chiusa, g.toString());
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


}
