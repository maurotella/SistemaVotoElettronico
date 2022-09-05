create table "Auditing"
(
    id        serial
        primary key,
    orario    timestamp,
    azione    integer
        references "Azioni_auditing",
    ruolo_chi varchar(10)
        constraint "Auditing_ruolo_chi_check"
            check ((ruolo_chi)::text = ANY
                   ((ARRAY ['GESTORE'::character varying, 'ELETTORE'::character varying])::text[])),
    chi       varchar(16)
        references "Persone"
);

alter table "Auditing"
    owner to postgres;

INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (3, '2022-09-03 23:28:29.234420', 1, 'GESTORE', 'RBNSRA92R50L113H');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (4, '2022-09-03 23:28:29.317393', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (5, '2022-09-04 11:17:48.775433', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (6, '2022-09-04 11:17:50.656024', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (7, '2022-09-04 11:18:10.090636', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (8, '2022-09-04 11:18:16.264359', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (9, '2022-09-04 11:18:53.715194', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (10, '2022-09-04 11:21:32.441496', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (11, '2022-09-04 11:21:47.846526', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (12, '2022-09-04 11:22:28.666050', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (13, '2022-09-04 11:22:41.012165', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (14, '2022-09-04 16:29:01.625901', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (15, '2022-09-04 18:49:36.893657', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (16, '2022-09-04 18:50:01.920335', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (17, '2022-09-04 18:50:26.339911', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (18, '2022-09-04 18:50:38.626528', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (19, '2022-09-04 18:55:21.060941', 1, 'GESTORE', 'RBNSRA92R50L113H');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (20, '2022-09-04 23:23:12.103630', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (21, '2022-09-04 23:25:17.690080', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (22, '2022-09-04 23:30:15.159280', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (23, '2022-09-04 23:34:53.512468', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (24, '2022-09-04 23:35:37.224751', 1, 'GESTORE', 'RBNSRA92R50L113H');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (25, '2022-09-05 00:00:24.602836', 1, 'GESTORE', 'RBNSRA92R50L113H');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (26, '2022-09-05 00:14:15.598345', 1, 'GESTORE', 'RBNSRA92R50L113H');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (27, '2022-09-05 00:15:01.115196', 1, 'ELETTORE', 'TLLMRA99L13H2640');
INSERT INTO sve."Auditing" (id, orario, azione, ruolo_chi, chi) VALUES (28, '2022-09-05 00:16:53.607626', 1, 'ELETTORE', 'TLLMRA99L13H2640');
