package data;

import models.*;
import util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessioneDAOImpl implements SessioneDAO {

    private static SessioneDAOImpl istance = null;

    private SessioneDAOImpl() {}

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

    @Override
    public boolean chiusa(Sessione S) {
        boolean res;
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT chiusa FROM \"Sessioni\" WHERE id=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, S.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            res = rs.getBoolean(1);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int nrElettori() {
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT count(*) FROM \"Elettori\"";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int nrVotanti(Sessione S) {
        if (!chiusa(S))
            throw new IllegalArgumentException("La sessione deve essere chiusa");
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT count(*) FROM \"VotiElettori\" WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, S.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean quorum(Sessione S) {
        boolean res;
        if (!chiusa(S))
            throw new IllegalArgumentException("La sessione deve essere chiusa");
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT count(*) FROM \"VotiElettori\" WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, S.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            res = rs.getInt(1) >= (nrElettori()/2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public HashMap<Boolean, Integer> esitiReferendum(Sessione S) {
        if (!chiusa(S))
            throw new IllegalArgumentException("La sessione deve essere chiusa");
        Connection db = DbManager.getInstance().getDb();
        String query = "SELECT si,no FROM \"Referendum\" WHERE sessione=?";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, S.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            HashMap<Boolean, Integer> esiti = new HashMap<>();
            esiti.put(true,rs.getInt("si"));
            esiti.put(false,rs.getInt("no"));
            stmt.close();
            rs.close();
            return esiti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<Candidato, Integer> esitiVotoCandidati(Sessione S) {
        Connection db = DbManager.getInstance().getDb();
        String query = """
                        SELECT C.id,VC.voti,C.persona,C.partito,C.ruolo FROM "VotiCandidati" as VC
                            JOIN "Candidati" C on C.id = VC.candidato
                            WHERE sessione=?;""";
        HashMap<Candidato, Integer> res = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            ResultSet rs = stmt.executeQuery();
            Candidato C;
            while (rs.next()) {
                C = new Candidato(
                        rs.getInt("id"),
                        rs.getString("persona"),
                        rs.getInt("partito"),
                        rs.getString("ruolo")
                );
                res.put(C,rs.getInt("voti"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public boolean maggioranza(Sessione S) {
        boolean res = false;
        Connection db = DbManager.getInstance().getDb();
        String query = """
                SELECT max(voti)::float/sum(voti) as percentuale FROM "VotiCandidati"
                                         WHERE sessione=?;""";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1,S.getId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getFloat("percentuale")>0.5)
                res = true;
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
