create table "VotiElettori"
(
    elettore varchar(16) not null
        references "Elettori",
    sessione integer     not null
        references "Sessioni",
    orario   timestamp,
    primary key (elettore, sessione)
);

alter table "VotiElettori"
    owner to postgres;

