package models;
/**
 * Classe che rappresenta un candidato
 */
public class Candidato {

    private final int id;
    private final Persona persona;
    private final Partito partito;
    private final String ruolo;

    /**
     * Crea un candidato
     *
     * @param id
     * @param persona
     * @param partito
     * @param ruolo
     */
    public Candidato(int id, Persona persona, Partito partito, String ruolo) {
        if (persona==null)
            throw new IllegalArgumentException("Persona null");
        if (partito==null)
            throw new IllegalArgumentException("Partito null");
        if (ruolo==null || ruolo.length()==0)
            throw new IllegalArgumentException("Ruolo null o vuoto");
        this.id = id;
        this.persona = persona;
        this.partito = partito;
        this.ruolo = ruolo;
    }

    public int getId() {
        return id;
    }

    public Persona getPersona() {
        return persona;
    }

    public Partito getPartito() {
        return partito;
    }

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return "Candidato{\n" +
                "   + id=" + id + "\n" +
                "   + persona=" + persona + "\n" +
                "   + partito=" + partito + "\n" +
                "   + ruolo='" + ruolo + '\'' + "\n" +
                '}';
    }
}
