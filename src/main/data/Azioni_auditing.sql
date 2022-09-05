create table "Azioni_auditing"
(
    id     serial
        constraint azioni_auditing_pkey
            primary key,
    azione varchar(50)
);

alter table "Azioni_auditing"
    owner to postgres;

INSERT INTO sve."Azioni_auditing" (id, azione) VALUES (1, 'LOGIN');
INSERT INTO sve."Azioni_auditing" (id, azione) VALUES (2, 'VOTAZIONE');
INSERT INTO sve."Azioni_auditing" (id, azione) VALUES (3, 'APERTURA_SESSIONE');
INSERT INTO sve."Azioni_auditing" (id, azione) VALUES (4, 'CHIUSURA_SESSIONE');
