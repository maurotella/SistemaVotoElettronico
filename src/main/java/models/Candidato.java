package models;

import java.util.Objects;

/**
 * Classe che rappresenta un candidato
 */
public class Candidato {

    private final int id;
    private final String persona;
    private final int partito;
    private final String ruolo;

    /**
     * Crea un candidato
     *
     * @param id
     * @param persona cf persona
     * @param partito id partito
     * @param ruolo
     */
    public Candidato(int id, String persona, int partito, String ruolo) {
        if (persona==null || persona.length()==0)
            throw new IllegalArgumentException("Persona null o vuota");
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

    public String getPersona() {
        return persona;
    }

    public int getPartito() {
        return partito;
    }

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return id == candidato.id && partito == candidato.partito && persona.equals(candidato.persona) && ruolo.equals(candidato.ruolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, persona, partito, ruolo);
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
