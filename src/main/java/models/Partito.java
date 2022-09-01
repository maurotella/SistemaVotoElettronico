package models;
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
}
