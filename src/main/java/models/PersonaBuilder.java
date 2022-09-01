package models;

import java.time.LocalDate;

public final class PersonaBuilder {
    private String nome;
    private String cognome;
    private String CF;
    private char sesso;
    private String nazionalita;
    private String luogoNascita;
    private LocalDate dataNascita;

    private PersonaBuilder() {
    }

    public static PersonaBuilder newBuilder() {
        return new PersonaBuilder();
    }

    public PersonaBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public PersonaBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public PersonaBuilder CF(String CF) {
        this.CF = CF;
        return this;
    }

    public PersonaBuilder sesso(char sesso) {
        this.sesso = sesso;
        return this;
    }

    public PersonaBuilder nazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
        return this;
    }

    public PersonaBuilder luogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
        return this;
    }

    public PersonaBuilder dataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public Persona build() {
        return new Persona(
                nome, cognome, CF, sesso,
                nazionalita, luogoNascita, dataNascita
        );
    }
}