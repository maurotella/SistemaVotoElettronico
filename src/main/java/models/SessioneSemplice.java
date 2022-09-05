package models;

/**
 * Classe per rappresentare una sessione salvondo solo
 * titolo e id
 */
public class SessioneSemplice {

    private final int id;
    private final String titolo;

    public SessioneSemplice(int id, String titolo) {
        this.id = id;
        this.titolo = titolo;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    @Override
    public String toString() {
        return titolo;
    }
}
