package data;

import models.Candidato;
import models.Partito;
import util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartitoDAOImpl implements PartitoDAO {

    private static PartitoDAOImpl istance = null;

    private PartitoDAOImpl() {};

    public static PartitoDAOImpl getInstance() {
        if (istance==null)
            istance = new PartitoDAOImpl();
        return istance;
    }

    @Override
    public HashMap<Partito, ArrayList<Candidato>> getListaPartiti() {
        Connection db = DbManager.getInstance().getDb();
        String query =
                "SELECT\n" +
                        "    P.nome as partito,\n" +
                        "    P.id as idPartito,\n" +
                        "    C.id as idCandidato,\n" +
                        "    C.persona as persona,\n" +
                        "    C.ruolo as ruolo\n" +
                        "    FROM \"Candidati\" AS C\n" +
                        "    JOIN \"Partiti\" AS P ON C.partito=P.id;";
        HashMap<Partito, ArrayList<Candidato>> res = new HashMap<>();
        try {
            PreparedStatement stmt = db.prepareStatement(query);
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
