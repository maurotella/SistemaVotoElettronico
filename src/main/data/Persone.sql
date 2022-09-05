create table "Persone"
(
    cf            varchar(16) not null
        constraint "Persona_pkey"
            primary key,
    nome          varchar(100),
    cognome       varchar(100),
    sesso         varchar(1)
        constraint "Persona_sesso_check"
            check ((sesso)::text = ANY ((ARRAY ['M'::character varying, 'F'::character varying])::text[])),
    nazionalita   varchar(3)
        constraint "Persona_nazionalita_check"
            check (char_length((nazionalita)::text) = 3),
    luogo_nascita varchar(50),
    data_nascita  date
);

alter table "Persone"
    owner to postgres;

INSERT INTO sve."Persone" (cf, nome, cognome, sesso, nazionalita, luogo_nascita, data_nascita) VALUES ('TLLMRA99L13H2640', 'Mauro', 'Tellaroli', 'M', 'ITA', 'Rho', '1999-07-13');
INSERT INTO sve."Persone" (cf, nome, cognome, sesso, nazionalita, luogo_nascita, data_nascita) VALUES ('RBNSRA92R50L113H', 'Sara', 'Rubini', 'F', 'ITA', 'Termoli', '1992-10-10');
