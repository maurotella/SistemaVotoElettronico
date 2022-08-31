package models;

import java.time.LocalDate;

public class Sessione {

    private int id;
    private String titolo;
    private LocalDate dataApertura;
    private LocalDate dataChiusura;
    private TipoVotazione tipoVotazione;
    private TipoScrutinio tipoScrutinio;
    private boolean chiusa;
    private Gestore gestore;

    public Sessione (
            int i,
            String t,
            LocalDate da,
            LocalDate dc,
            TipoVotazione tv,
            TipoScrutinio ts,
            Gestore g
    ) {
        if (t==null || t.length()==0)
            throw new IllegalArgumentException("titolo null o vuoto");
        if (da==null)
            throw new IllegalArgumentException("dataApertura null");
        if (dc==null)
            throw new IllegalArgumentException("dataChiusura null");
        if (dc.compareTo(da)<=0)
            throw new IllegalArgumentException("dataChiusura deve essere successiva a dataChiusura");
        if (tv==null)
            throw new IllegalArgumentException("tipoVotazione null");
        if (ts==null)
            throw new IllegalArgumentException("tipoScrutinio null");
        if (g==null)
            throw new IllegalArgumentException("gestore null");
        id = i;
        titolo = t;
        dataApertura = da;
        dataChiusura = dc;
        tipoVotazione = tv;
        tipoScrutinio = ts;
        chiusa = false;
        gestore = g;
    };

    @Override
    public String toString() {
        return "Sessione{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", dataApertura=" + dataApertura +
                ", dataChiusura=" + dataChiusura +
                ", tipoVotazione=" + tipoVotazione +
                ", tipoScrutinio=" + tipoScrutinio +
                ", chiusa=" + chiusa +
                ", gestore=" + gestore.getPersona().getNominativo() +
                '}';
    }

    public static void main (String[] args) {
        Persona P = new Persona("Mario","Rossi","0123456789ABDEFG","Rho",LocalDate.of(1999,07,13));
        Sessione S = SessioneBuilder.newBuilder(1)
                .titolo("Prova")
                .dataApertura(LocalDate.of(2022,9,10))
                .dataChiusura(LocalDate.of(2022,10,10))
                .tipoVotazione(TipoVotazione.CATEGORICO)
                .tipoScrutinio(TipoScrutinio.REFERENDUM)
                .gestore(new Gestore(P,"Gino"))
                .build();

        System.out.println(S.toString());
    }
}
