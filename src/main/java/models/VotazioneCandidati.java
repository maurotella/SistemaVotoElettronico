package models;

public class VotazioneCandidati extends Votazione{

    private final int candidato;

    public VotazioneCandidati(Sessione sessione, int candidato, int voti) {
        super(sessione);
        if (voti<0)
            throw new IllegalArgumentException("numero voti negativo");
        this.candidato = candidato;
    }

    public int getCandidato() {
        return candidato;
    }

}
