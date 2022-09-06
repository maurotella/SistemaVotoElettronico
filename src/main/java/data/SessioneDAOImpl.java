package data;

import models.Sessione;
import models.SessioneBuilder;
import models.TipoScrutinio;
import models.TipoVotazione;
import util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessioneDAOImpl implements SessioneDAO {

    private static SessioneDAOImpl istance = null;

    private SessioneDAOImpl() {};

    public static SessioneDAOImpl getInstance() {
        if (istance==null)
            istance = new SessioneDAOImpl();
        return istance;
    }

    @Override
    public Sessione getSessione(int id) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Sessioni\" WHERE id=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new RuntimeException("Id sessione inesistente");
            return SessioneBuilder.newBuilder(id)
                    .titolo(rs.getString("titolo"))
                    .dataApertura(Util.dateToLocal(rs.getDate("data_apertura")))
                    .dataChiusura(Util.dateToLocal(rs.getDate("data_chiusura")))
                    .tipoVotazione(TipoVotazione.valueOf(rs.getString("tipo_votazione")))
                    .tipoScrutinio(TipoScrutinio.valueOf(rs.getString("tipo_scrutinio")))
                    .gestore(rs.getString("gestore"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
