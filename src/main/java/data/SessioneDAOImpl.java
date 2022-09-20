package data;

import models.*;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            Sessione S = SessioneBuilder.newBuilder(id)
                    .titolo(rs.getString("titolo"))
                    .dataApertura(Util.dateToLocal(rs.getDate("data_apertura")))
                    .dataChiusura(Util.dateToLocal(rs.getDate("data_chiusura")))
                    .tipoVotazione(TipoVotazione.valueOf(rs.getString("tipo_votazione")))
                    .tipoScrutinio(TipoScrutinio.valueOf(rs.getString("tipo_scrutinio")))
                    .gestore(rs.getString("gestore"))
                    .build();
            if (rs.getBoolean("chiusa"))
                    S.chiudi();
            return S;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Referendum getReferendum(int id) {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT * FROM \"Referendum\" WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                return null;
            Referendum R = new Referendum(
                    SessioneDAOImpl.getInstance().getSessione(rs.getInt("sessione")),
                    rs.getString("quesito"),
                    rs.getInt("si"),
                    rs.getInt("no")
            );
            stmt.close();
            rs.close();
            return R;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Partito, ArrayList<Candidato>> getListaCandidati(Sessione S) {
        Connection db = DbManager.getInstance().getDb();
        String query =
                "SELECT " +
                        "P.nome as partito," +
                        "P.id as idPartito," +
                        "C.id as idCandidato," +
                        "C.persona as persona," +
                        "C.ruolo as ruolo " +
                "FROM \"VotiCandidati\" AS VC " +
                "JOIN \"Candidati\" AS C ON VC.candidato=C.id " +
                "JOIN \"Partiti\" AS P ON C.partito=P.id " +
                "WHERE VC.sessione = ?";
        HashMap<Partito, ArrayList<Candidato>> res = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            ResultSet rs = stmt.executeQuery();
            Partito P;
            Candidato C;
            while (rs.next()) {
                P = new Partito(
                        rs.getInt("idPartito"),
                        rs.getString("partito")
                );
                C = new Candidato(
                        rs.getInt("idCandidato"),
                        rs.getString("persona"),
                        P.getId(),
                        rs.getString("ruolo")
                );
                res.put(
                        P,
                        res.containsKey(P) ? Util.addAndReturnList(res.get(P), C) : new ArrayList<>(List.of(C))
                );
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
