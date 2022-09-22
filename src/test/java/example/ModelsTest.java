package example;

import data.ElettoreDAOImpl;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;


public class ModelsTest {

    @DisplayName("Test sessione data chiusura")
    @Test
    void test1SessioneDataChiusura() {
        // Data chiusura precedente alla data di apertura
        assertThrows( IllegalArgumentException.class, () -> {
            SessioneBuilder.newBuilder(34)
                    .titolo("Test")
                    .gestore("E")
                    .dataApertura(LocalDate.now())
                    .dataChiusura(LocalDate.of(2000,9,25))
                    .tipoVotazione(TipoVotazione.REFERENDUM)
                    .tipoScrutinio(TipoScrutinio.REFERENDUM)
                    .votazionePartiti(false)
                    .build();
        });
    }

    @DisplayName("Test sessione data apertura")
    @Test
    void test2SessioneDataApertura() {
        //Data di apertura precedente a LocalDate.now();
        assertThrows( IllegalArgumentException.class, () -> {
            SessioneBuilder.newBuilder(34)
                    .titolo("Test")
                    .gestore("E")
                    .dataApertura(LocalDate.of(1999, 7, 13))
                    .dataChiusura(LocalDate.of(2000,9,25))
                    .tipoVotazione(TipoVotazione.REFERENDUM)
                    .tipoScrutinio(TipoScrutinio.REFERENDUM)
                    .votazionePartiti(false)
                    .build();
        });
    }

    @DisplayName("Test sessione votazione scrutinio")
    @Test
    void test3SessioneVotazioneScrutinio() {
        //Tipo votazione e scrutinio incompatibili;
        assertThrows( IllegalArgumentException.class, () -> {
            SessioneBuilder.newBuilder(34)
                    .titolo("Test")
                    .gestore("E")
                    .dataApertura(LocalDate.of(2024, 7, 13))
                    .dataChiusura(LocalDate.of(2025,9,25))
                    .tipoVotazione(TipoVotazione.REFERENDUM)
                    .tipoScrutinio(TipoScrutinio.MAGGIORANZA_ASSOLUTA)
                    .votazionePartiti(false)
                    .build();
        });
    }

    @DisplayName("Test persona sesso")
    @Test
    void test4PersonaSesso() {
        //Sesso diverso da 'M' o 'F';
        assertThrows( IllegalArgumentException.class, () -> {
            PersonaBuilder.newBuilder()
                    .nome("Mario")
                    .cognome("Rossi")
                    .CF("RSSMRA80A01F205X")
                    .sesso('A')
                    .nazionalita("ITA")
                    .luogoNascita("Milano")
                    .dataNascita(LocalDate.of(1980,1,1))
                    .build();
        });
    }

    @DisplayName("Test persona CF")
    @Test
    void test5PersonaCF() {
        //controllo CF;
        assertThrows( IllegalArgumentException.class, () -> {
            PersonaBuilder.newBuilder()
                    .nome("Mario")
                    .cognome("Fumagalli")
                    .CF("RSSMRA80A01F205X")
                    .sesso('M')
                    .nazionalita("ITA")
                    .luogoNascita("Milano")
                    .dataNascita(LocalDate.of(1980,1,1))
                    .build();
        });
    }

    @DisplayName("Test persona nazionalità")
    @Test
    void test6PersonaNazionalita() {
        //Controllo nazionalita.length()==3;
        assertThrows( IllegalArgumentException.class, () -> {
            PersonaBuilder.newBuilder()
                    .nome("Mario")
                    .cognome("Rossi")
                    .CF("RSSMRA80A01F205X")
                    .sesso('M')
                    .nazionalita("ITALIANA")
                    .luogoNascita("Milano")
                    .dataNascita(LocalDate.of(1980,1,1))
                    .build();
        });
    }

    @DisplayName("Test votazione numero voti")
    @Test
    void test7Votazione() {
        //Numero voti negativo;
        Sessione S = SessioneBuilder.newBuilder(34)
                .titolo("Test")
                .gestore("E")
                .dataApertura(LocalDate.of(2024, 7, 13))
                .dataChiusura(LocalDate.of(2025,9,25))
                .tipoVotazione(TipoVotazione.REFERENDUM)
                .tipoScrutinio(TipoScrutinio.REFERENDUM)
                .votazionePartiti(false)
                .build();
        assertThrows( IllegalArgumentException.class, () -> {
            new VotazioneCandidati(S,2, -5);
        });
    }

}
