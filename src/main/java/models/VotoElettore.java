package models;

import java.time.LocalDateTime;

public class VotoElettore {

    // username dell'elettore che ha votato
    private final String elettore;
    // id della sessione in cui si è votato
    private final int sessione;
    // orario in cui si ha votato
    private final LocalDateTime orario;

    public VotoElettore(String elettore, int sessione, LocalDateTime orario) {
        if (elettore==null || elettore.length()==0)
            throw new IllegalArgumentException("elettore null o vuoto");
        if (orario==null)
            throw new IllegalArgumentException("orario null");
        this.elettore = elettore;
        this.sessione = sessione;
        this.orario = orario;
    }

    public String getElettore() {
        return elettore;
    }

    public int getSessione() {
        return sessione;
    }

    public LocalDateTime getOrario() {
        return orario;
    }

    @Override
    public String toString() {
        return "VotoElettore{\n" +
                "    elettore='" + elettore + '\'' + "\n" +
                "    sessione=" + sessione + "\n" +
                "    orario=" + orario + "\n" +
                '}';
    }
}
