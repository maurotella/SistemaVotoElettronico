create table "Elettori"
(
    cf                    varchar(16) not null
        constraint "Elettore_pkey"
            primary key
        constraint "Elettore_cf_fkey"
            references "Persone",
    password              varchar(60),
    diritto_voto          boolean,
    diritto_voto_distanza boolean
);

alter table "Elettori"
    owner to postgres;

INSERT INTO sve."Elettori" (cf, password, diritto_voto, diritto_voto_distanza) VALUES ('TLLMRA99L13H2640', '$2a$10$fKo1OxDBeksu4x2acSae4u3JLP2FJ7VDA5DFBhukI1tY3fVHIDtRq', true, false);
