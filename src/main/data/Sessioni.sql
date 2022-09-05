create table "Sessioni"
(
    id             integer default nextval('sve."Sessione_id_seq"'::regclass) not null
        constraint "Sessione_pkey"
            primary key,
    titolo         varchar(100),
    data_apertura  date
        constraint data_apertura_check
            check (data_apertura >= CURRENT_DATE),
    data_chiusura  date,
    tipo_votazione varchar(21)
        constraint tipo_votazione_check
            check ((tipo_votazione)::text = ANY
                   ((ARRAY ['ORDINALE'::character varying, 'CATEGORICO'::character varying, 'CATEGORICO_PREFERENZA'::character varying, 'REFERENDUM'::character varying])::text[])),
    tipo_scrutinio varchar(20)
        constraint tipo_scrutinio_check
            check ((tipo_scrutinio)::text = ANY
                   ((ARRAY ['MAGGIORANZA'::character varying, 'MAGGIORANZA_ASSOLUTA'::character varying, 'REFERENDUM'::character varying, 'REFERENDUM_QUORUM'::character varying])::text[])),
    chiusa         boolean,
    gestore        varchar(16)
        constraint "Sessione_gestore_fkey"
            references "Gestori",
    constraint data_chiusura_check
        check (data_apertura < data_chiusura)
);

alter table "Sessioni"
    owner to postgres;

INSERT INTO sve."Sessioni" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) VALUES (1, 'Eutanasia', '2022-09-02', '2022-09-04', 'REFERENDUM', 'REFERENDUM', false, 'RBNSRA92R50L113H');
INSERT INTO sve."Sessioni" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) VALUES (2, 'Indipendenza nord', '2022-09-06', '2022-09-16', 'REFERENDUM', 'REFERENDUM_QUORUM', false, 'TLLMRA99L13H2640');
INSERT INTO sve."Sessioni" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) VALUES (3, 'Reddito di cittadinanza', '2022-09-13', '2022-09-18', 'REFERENDUM', 'REFERENDUM', false, 'RBNSRA92R50L113H');
