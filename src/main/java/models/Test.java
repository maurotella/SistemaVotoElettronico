package models;

import java.time.LocalDate;

public class Test {

    public static void print (Object S) {
        System.out.println(S.toString());
    }

    public static void main (String[] args) {

        Persona P = new Persona("Mario","Rossi","RSSMRA99L13H264X",'M',"ITA","Rho", LocalDate.of(1999,07,13));
        Gestore G = new Gestore("RSSMRA99L13H264X");
        Sessione S = SessioneBuilder.newBuilder(1)
                .titolo("Prova")
                .dataApertura(LocalDate.of(2022,9,10))
                .dataChiusura(LocalDate.of(2022,10,10))
                .tipoVotazione(TipoVotazione.REFERENDUM)
                .tipoScrutinio(TipoScrutinio.REFERENDUM)
                .gestore(G.getCF())
                .build();

        VotiReferendum R = new VotiReferendum(S, "Eutanasia legale?");

        print(G);

    }

}
