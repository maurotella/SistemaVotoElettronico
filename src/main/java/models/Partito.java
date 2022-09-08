package models;

import java.util.Objects;

/**
 * Classe che rappresenta un partito formato
 * da candidati, con un nome e un logo
 */
public class Partito {

    private final int id;
    private final String nome;

    /**
     * Crea un partico con id e nome
     *
     * @param id id
     * @param n nome
     */
    public Partito (int id, String n) {
        if (n==null || n.length()==0)
            throw new IllegalArgumentException("nome null o vuoto");
        this.id = id;
        nome = n;
    }

    /**
     * Reistituisce l'id del partito
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Reistituisce il nome del partito
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partito partito = (Partito) o;
        return id == partito.id && nome.equals(partito.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Partito{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
