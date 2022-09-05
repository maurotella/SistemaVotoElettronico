create table "Gestori"
(
    cf       varchar(16) not null
        constraint "Gestore_pkey"
            primary key
        constraint "Gestore_cf_fkey"
            references "Persone",
    password varchar(60)
);

alter table "Gestori"
    owner to postgres;

INSERT INTO sve."Gestori" (cf, password) VALUES ('RBNSRA92R50L113H', '$2a$10$.PIxbqfYnUCx9oxrD4A0.eLBMytou4oYkbIqmlChlUEau89BkIYQu');
INSERT INTO sve."Gestori" (cf, password) VALUES ('TLLMRA99L13H2640', '$2a$10$YQnGQVRzCurbwcbavzZgmOBSaiKU7fqWYiDXybBsUhsnGjJ41a6GC');
