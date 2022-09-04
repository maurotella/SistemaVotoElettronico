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
-- Name: Elettore; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Elettore" (
    cf character varying(16) NOT NULL,
    password character varying(60),
    dirittovoto boolean,
    dirittovotodistanza boolean
);


ALTER TABLE sve."Elettore" OWNER TO postgres;

--
-- Name: Gestore; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Gestore" (
    cf character varying(16) NOT NULL,
    password character varying(60)
);


ALTER TABLE sve."Gestore" OWNER TO postgres;

--
-- Name: Persona; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Persona" (
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


ALTER TABLE sve."Persona" OWNER TO postgres;

--
-- Name: Sessione; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Sessione" (
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


ALTER TABLE sve."Sessione" OWNER TO postgres;

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

ALTER SEQUENCE sve."Sessione_id_seq" OWNED BY sve."Sessione".id;


--
-- Name: Sessione id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessione" ALTER COLUMN id SET DEFAULT nextval('sve."Sessione_id_seq"'::regclass);


--
-- Data for Name: Elettore; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Elettore" (cf, password, dirittovoto, dirittovotodistanza) FROM stdin;
TLLMRA99L13H2640	$2a$10$fKo1OxDBeksu4x2acSae4u3JLP2FJ7VDA5DFBhukI1tY3fVHIDtRq	t	f
\.


--
-- Data for Name: Gestore; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Gestore" (cf, password) FROM stdin;
RBNSRA92R50L113H	$2a$10$.PIxbqfYnUCx9oxrD4A0.eLBMytou4oYkbIqmlChlUEau89BkIYQu
\.


--
-- Data for Name: Persona; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Persona" (cf, nome, cognome, sesso, nazionalita, luogo_nascita, data_nascita) FROM stdin;
TLLMRA99L13H2640	Mauro	Tellaroli	M	ITA	Rho	1999-07-13
RBNSRA92R50L113H	Sara	Rubini	F	ITA	Termoli	1992-10-10
\.


--
-- Data for Name: Sessione; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Sessione" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore) FROM stdin;
\.


--
-- Name: Sessione_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Sessione_id_seq"', 1, false);


--
-- Name: Elettore Elettore_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Elettore"
    ADD CONSTRAINT "Elettore_pkey" PRIMARY KEY (cf);


--
-- Name: Gestore Gestore_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Gestore"
    ADD CONSTRAINT "Gestore_pkey" PRIMARY KEY (cf);


--
-- Name: Persona Persona_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Persona"
    ADD CONSTRAINT "Persona_pkey" PRIMARY KEY (cf);


--
-- Name: Sessione Sessione_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessione"
    ADD CONSTRAINT "Sessione_pkey" PRIMARY KEY (id);


--
-- Name: Elettore Elettore_cf_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Elettore"
    ADD CONSTRAINT "Elettore_cf_fkey" FOREIGN KEY (cf) REFERENCES sve."Persona"(cf);


--
-- Name: Gestore Gestore_cf_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Gestore"
    ADD CONSTRAINT "Gestore_cf_fkey" FOREIGN KEY (cf) REFERENCES sve."Persona"(cf);


--
-- Name: Sessione Sessione_gestore_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessione"
    ADD CONSTRAINT "Sessione_gestore_fkey" FOREIGN KEY (gestore) REFERENCES sve."Gestore"(cf);


--
-- PostgreSQL database dump complete
--

