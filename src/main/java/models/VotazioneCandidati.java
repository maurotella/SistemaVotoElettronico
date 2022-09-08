package models;

public class VotazioneCandidati {

    private final int sessione;
    private final int candidato;
    private final int voti;


    public VotazioneCandidati(int sessione, int candidato, int voti) {
        if (voti<0)
            throw new IllegalArgumentException("numero voti negativo");
        this.sessione = sessione;
        this.candidato = candidato;
        this.voti = voti;
    }

    public int getSessione() {
        return sessione;
    }

    public int getCandidato() {
        return candidato;
    }

    public int getVoti() {
        return voti;
    }

}
