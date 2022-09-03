--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: sve; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA sve;


ALTER SCHEMA sve OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Auditing; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Auditing" (
    id integer NOT NULL,
    orario timestamp without time zone,
    azione integer,
    ruolo_chi character varying(10),
    chi character varying(16),
    CONSTRAINT "Auditing_ruolo_chi_check" CHECK (((ruolo_chi)::text = ANY ((ARRAY['GESTORE'::character varying, 'ELETTORE'::character varying])::text[])))
);


ALTER TABLE sve."Auditing" OWNER TO postgres;

--
-- Name: Auditing_id_seq; Type: SEQUENCE; Schema: sve; Owner: postgres
--

CREATE SEQUENCE sve."Auditing_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sve."Auditing_id_seq" OWNER TO postgres;

--
-- Name: Auditing_id_seq; Type: SEQUENCE OWNED BY; Schema: sve; Owner: postgres
--

ALTER SEQUENCE sve."Auditing_id_seq" OWNED BY sve."Auditing".id;


--
-- Name: Azioni_auditing; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Azioni_auditing" (
    id integer NOT NULL,
    azione character varying(50)
);


ALTER TABLE sve."Azioni_auditing" OWNER TO postgres;

--
-- Name: Azioni_auditing_id_seq; Type: SEQUENCE; Schema: sve; Owner: postgres
--

CREATE SEQUENCE sve."Azioni_auditing_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sve."Azioni_auditing_id_seq" OWNER TO postgres;

--
-- Name: Azioni_auditing_id_seq; Type: SEQUENCE OWNED BY; Schema: sve; Owner: postgres
--

ALTER SEQUENCE sve."Azioni_auditing_id_seq" OWNED BY sve."Azioni_auditing".id;


--
-- Name: Elettori; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Elettori" (
    cf character varying(16) NOT NULL,
    password character varying(60),
    dirittovoto boolean,
    dirittovotodistanza boolean
);


ALTER TABLE sve."Elettori" OWNER TO postgres;

--
-- Name: Gestori; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Gestori" (
    cf character varying(16) NOT NULL,
    password character varying(60)
);


ALTER TABLE sve."Gestori" OWNER TO postgres;

--
-- Name: Persone; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Persone" (
    cf character varying(16) NOT NULL,
    nome character varying(100),
    cognome character varying(100),
    sesso character varying(1),
    nazionalita character varying(3),
    luogo_nascita character varying(50),
    data_nascita date,
    CONSTRAINT "Persona_nazionalita_check" CHECK ((char_length((nazionalita)::text) = 3)),
    CONSTRAINT "Persona_sesso_check" CHECK (((sesso)::text = ANY ((ARRAY['M'::character varying, 'F'::character varying])::text[])))
);


ALTER TABLE sve."Persone" OWNER TO postgres;

--
-- Name: Sessioni; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Sessioni" (
    id integer NOT NULL,
    titolo character varying(100),
    data_apertura date,
    data_chiusura date,
    tipo_votazione character varying(21),
    tipo_scrutinio character varying(20),
    chiusa boolean,
    gestore character varying(16),
    CONSTRAINT data_apertura_check CHECK ((data_apertura >= CURRENT_DATE)),
    CONSTRAINT data_chiusura_check CHECK ((data_apertura < data_chiusura)),
    CONSTRAINT tipo_scrutinio_check CHECK (((tipo_scrutinio)::text = ANY ((ARRAY['MAGGIORANZA'::character varying, 'MAGGIORANZA_ASSOLUTA'::character varying, 'REFERENDUM'::character varying, 'REFERENDUM_QUORUM'::character varying])::text[]))),
    CONSTRAINT tipo_votazione_check CHECK (((tipo_votazione)::text = ANY ((ARRAY['ORDINALE'::character varying, 'CATEGORICO'::character varying, 'CATEGORICO_PREFERENZA'::character varying, 'REFERENDUM'::character varying])::text[])))
);


ALTER TABLE sve."Sessioni" OWNER TO postgres;

--
-- Name: Sessione_id_seq; Type: SEQUENCE; Schema: sve; Owner: postgres
--

CREATE SEQUENCE sve."Sessione_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sve."Sessione_id_seq" OWNER TO postgres;

--
-- Name: Sessione_id_seq; Type: SEQUENCE OWNED BY; Schema: sve; Owner: postgres
--

ALTER SEQUENCE sve."Sessione_id_seq" OWNED BY sve."Sessioni".id;


--
-- Name: Auditing id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing" ALTER COLUMN id SET DEFAULT nextval('sve."Auditing_id_seq"'::regclass);


--
-- Name: Azioni_auditing id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Azioni_auditing" ALTER COLUMN id SET DEFAULT nextval('sve."Azioni_auditing_id_seq"'::regclass);


--
-- Name: Sessioni id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni" ALTER COLUMN id SET DEFAULT nextval('sve."Sessione_id_seq"'::regclass);


--
-- Data for Name: Auditing; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Auditing" (id, orario, azione, ruolo_chi, chi) FROM stdin;
\.


--
-- Data for Name: Azioni_auditing; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Azioni_auditing" (id, azione) FROM stdin;
1	LOGIN
2	VOTAZIONE
3	APERTURA_SESSIONE
4	CHIUSURA_SESSIONE
\.


--
-- Data for Name: Elettori; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Elettori" (cf, password, dirittovoto, dirittovotodistanza) FROM stdin;
TLLMRA99L13H2640	$2a$10$fKo1OxDBeksu4x2acSae4u3JLP2FJ7VDA5DFBhukI1tY3fVHIDtRq	t	f
\.


--
-- Data for Name: Gestori; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Gestori" (cf, password) FROM stdin;
RBNSRA92R50L113H	$2a$10$.PIxbqfYnUCx9oxrD4A0.eLBMytou4oYkbIqmlChlUEau89BkIYQu
TLLMRA99L13H2640	$2a$10$YQnGQVRzCurbwcbavzZgmOBSaiKU7fqWYiDXybBsUhsnGjJ41a6GC
\.


--
-- Data for Name: Persone; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Persone" (cf, nome, cognome, sesso, nazionalita, luogo_nascita, data_nascita) FROM stdin;
TLLMRA99L13H2640	Mauro	Tellaroli	M	ITA	Rho	1999-07-13
RBNSRA92R50L113H	Sara	Rubini	F	ITA	Termoli	1992-10-10
\.


--
-- Data for Name: Sessioni; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Sessioni" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) FROM stdin;
1	Eutanasia	2022-09-02	2022-09-04	REFERENDUM	REFERENDUM	f	RBNSRA92R50L113H
2	Indipendenza nord	2022-09-06	2022-09-16	REFERENDUM	REFERENDUM_QUORUM	f	TLLMRA99L13H2640
3	Reddito di cittadinanza	2022-09-13	2022-09-18	REFERENDUM	REFERENDUM	f	RBNSRA92R50L113H
\.


--
-- Name: Auditing_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Auditing_id_seq"', 2, true);


--
-- Name: Azioni_auditing_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Azioni_auditing_id_seq"', 4, true);


--
-- Name: Sessione_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Sessione_id_seq"', 4, true);


--
-- Name: Auditing Auditing_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing"
    ADD CONSTRAINT "Auditing_pkey" PRIMARY KEY (id);


--
-- Name: Elettori Elettore_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Elettori"
    ADD CONSTRAINT "Elettore_pkey" PRIMARY KEY (cf);


--
-- Name: Gestori Gestore_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Gestori"
    ADD CONSTRAINT "Gestore_pkey" PRIMARY KEY (cf);


--
-- Name: Persone Persona_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Persone"
    ADD CONSTRAINT "Persona_pkey" PRIMARY KEY (cf);


--
-- Name: Sessioni Sessione_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni"
    ADD CONSTRAINT "Sessione_pkey" PRIMARY KEY (id);


--
-- Name: Azioni_auditing azioni_auditing_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Azioni_auditing"
    ADD CONSTRAINT azioni_auditing_pkey PRIMARY KEY (id);


--
-- Name: Auditing Auditing_azione_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing"
    ADD CONSTRAINT "Auditing_azione_fkey" FOREIGN KEY (azione) REFERENCES sve."Azioni_auditing"(id);


--
-- Name: Auditing Auditing_chi_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing"
    ADD CONSTRAINT "Auditing_chi_fkey" FOREIGN KEY (chi) REFERENCES sve."Persone"(cf);


--
-- Name: Elettori Elettore_cf_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Elettori"
    ADD CONSTRAINT "Elettore_cf_fkey" FOREIGN KEY (cf) REFERENCES sve."Persone"(cf);


--
-- Name: Gestori Gestore_cf_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Gestori"
    ADD CONSTRAINT "Gestore_cf_fkey" FOREIGN KEY (cf) REFERENCES sve."Persone"(cf);


--
-- Name: Sessioni Sessione_gestore_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni"
    ADD CONSTRAINT "Sessione_gestore_fkey" FOREIGN KEY (gestore) REFERENCES sve."Gestori"(cf);


--
-- PostgreSQL database dump complete
--

