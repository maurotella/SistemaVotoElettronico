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

DROP DATABASE IF EXISTS "sveData";
--
-- Name: sveData; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "sveData" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';


ALTER DATABASE "sveData" OWNER TO postgres;

\connect "sveData"

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
-- Name: Candidati; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Candidati" (
    id integer NOT NULL,
    persona character varying(16),
    partito integer,
    ruolo character varying(50)
);


ALTER TABLE sve."Candidati" OWNER TO postgres;

--
-- Name: Candidati_int_seq; Type: SEQUENCE; Schema: sve; Owner: postgres
--

CREATE SEQUENCE sve."Candidati_int_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sve."Candidati_int_seq" OWNER TO postgres;

--
-- Name: Candidati_int_seq; Type: SEQUENCE OWNED BY; Schema: sve; Owner: postgres
--

ALTER SEQUENCE sve."Candidati_int_seq" OWNED BY sve."Candidati".id;


--
-- Name: Elettori; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Elettori" (
    cf character varying(16) NOT NULL,
    password character varying(60),
    diritto_voto boolean,
    diritto_voto_distanza boolean
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
-- Name: Partiti; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Partiti" (
    id integer NOT NULL,
    nome character varying(100)
);


ALTER TABLE sve."Partiti" OWNER TO postgres;

--
-- Name: Partiti_id_seq; Type: SEQUENCE; Schema: sve; Owner: postgres
--

CREATE SEQUENCE sve."Partiti_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sve."Partiti_id_seq" OWNER TO postgres;

--
-- Name: Partiti_id_seq; Type: SEQUENCE OWNED BY; Schema: sve; Owner: postgres
--

ALTER SEQUENCE sve."Partiti_id_seq" OWNED BY sve."Partiti".id;


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
-- Name: Referendum; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."Referendum" (
    sessione integer NOT NULL,
    quesito character varying(200) NOT NULL,
    si integer DEFAULT 0,
    no integer DEFAULT 0
);


ALTER TABLE sve."Referendum" OWNER TO postgres;

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
    "votazionePartiti" boolean DEFAULT false,
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
-- Name: VotiCandidati; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."VotiCandidati" (
    sessione integer,
    candidato integer,
    voti integer DEFAULT 0
);


ALTER TABLE sve."VotiCandidati" OWNER TO postgres;

--
-- Name: VotiElettori; Type: TABLE; Schema: sve; Owner: postgres
--

CREATE TABLE sve."VotiElettori" (
    elettore character varying(16) NOT NULL,
    sessione integer NOT NULL,
    orario timestamp without time zone
);


ALTER TABLE sve."VotiElettori" OWNER TO postgres;

--
-- Name: Auditing id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing" ALTER COLUMN id SET DEFAULT nextval('sve."Auditing_id_seq"'::regclass);


--
-- Name: Azioni_auditing id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Azioni_auditing" ALTER COLUMN id SET DEFAULT nextval('sve."Azioni_auditing_id_seq"'::regclass);


--
-- Name: Candidati id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Candidati" ALTER COLUMN id SET DEFAULT nextval('sve."Candidati_int_seq"'::regclass);


--
-- Name: Partiti id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Partiti" ALTER COLUMN id SET DEFAULT nextval('sve."Partiti_id_seq"'::regclass);


--
-- Name: Sessioni id; Type: DEFAULT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni" ALTER COLUMN id SET DEFAULT nextval('sve."Sessione_id_seq"'::regclass);


--
-- Data for Name: Auditing; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Auditing" (id, orario, azione, ruolo_chi, chi) FROM stdin;
3	2022-09-03 23:28:29.23442	1	GESTORE	RBNSRA92R50L113H
4	2022-09-03 23:28:29.317393	1	ELETTORE	TLLMRA99L13H2640
5	2022-09-04 11:17:48.775433	1	ELETTORE	TLLMRA99L13H2640
6	2022-09-04 11:17:50.656024	1	ELETTORE	TLLMRA99L13H2640
7	2022-09-04 11:18:10.090636	1	ELETTORE	TLLMRA99L13H2640
8	2022-09-04 11:18:16.264359	1	ELETTORE	TLLMRA99L13H2640
9	2022-09-04 11:18:53.715194	1	ELETTORE	TLLMRA99L13H2640
10	2022-09-04 11:21:32.441496	1	ELETTORE	TLLMRA99L13H2640
11	2022-09-04 11:21:47.846526	1	ELETTORE	TLLMRA99L13H2640
12	2022-09-04 11:22:28.66605	1	ELETTORE	TLLMRA99L13H2640
13	2022-09-04 11:22:41.012165	1	ELETTORE	TLLMRA99L13H2640
14	2022-09-04 16:29:01.625901	1	ELETTORE	TLLMRA99L13H2640
15	2022-09-04 18:49:36.893657	1	ELETTORE	TLLMRA99L13H2640
16	2022-09-04 18:50:01.920335	1	ELETTORE	TLLMRA99L13H2640
17	2022-09-04 18:50:26.339911	1	ELETTORE	TLLMRA99L13H2640
18	2022-09-04 18:50:38.626528	1	ELETTORE	TLLMRA99L13H2640
19	2022-09-04 18:55:21.060941	1	GESTORE	RBNSRA92R50L113H
20	2022-09-04 23:23:12.10363	1	ELETTORE	TLLMRA99L13H2640
21	2022-09-04 23:25:17.69008	1	ELETTORE	TLLMRA99L13H2640
22	2022-09-04 23:30:15.15928	1	ELETTORE	TLLMRA99L13H2640
23	2022-09-04 23:34:53.512468	1	ELETTORE	TLLMRA99L13H2640
24	2022-09-04 23:35:37.224751	1	GESTORE	RBNSRA92R50L113H
25	2022-09-05 00:00:24.602836	1	GESTORE	RBNSRA92R50L113H
26	2022-09-05 00:14:15.598345	1	GESTORE	RBNSRA92R50L113H
27	2022-09-05 00:15:01.115196	1	ELETTORE	TLLMRA99L13H2640
28	2022-09-05 00:16:53.607626	1	ELETTORE	TLLMRA99L13H2640
29	2022-09-05 10:45:24.554487	1	ELETTORE	TLLMRA99L13H2640
30	2022-09-05 11:29:49.176443	1	ELETTORE	E
31	2022-09-05 11:31:22.430541	1	GESTORE	E
32	2022-09-05 12:18:22.815484	1	ELETTORE	E
33	2022-09-05 12:18:39.344461	1	ELETTORE	E
34	2022-09-05 12:18:48.062321	1	ELETTORE	E
35	2022-09-05 12:27:28.371685	1	ELETTORE	E
36	2022-09-05 12:30:15.23705	1	ELETTORE	E
37	2022-09-05 12:31:38.423506	1	ELETTORE	E
38	2022-09-05 12:34:00.514287	1	ELETTORE	E
39	2022-09-05 12:41:37.467488	1	ELETTORE	E
40	2022-09-05 12:44:38.477637	1	ELETTORE	E
41	2022-09-05 12:45:46.525694	1	ELETTORE	E
42	2022-09-05 12:46:04.011433	1	ELETTORE	E
43	2022-09-05 12:48:23.048121	1	ELETTORE	E
44	2022-09-05 12:48:44.4185	1	ELETTORE	E
45	2022-09-05 12:50:17.141753	1	ELETTORE	E
46	2022-09-05 14:16:32.04224	1	ELETTORE	E
47	2022-09-05 14:17:54.38875	1	ELETTORE	E
48	2022-09-05 14:19:06.422815	1	ELETTORE	E
49	2022-09-05 14:20:04.075895	1	ELETTORE	E
50	2022-09-05 14:21:42.708623	1	ELETTORE	E
51	2022-09-05 14:22:31.920843	1	ELETTORE	E
52	2022-09-05 14:24:30.550437	1	ELETTORE	E
53	2022-09-05 15:30:18.756678	1	ELETTORE	E
54	2022-09-05 15:30:39.41062	1	ELETTORE	E
55	2022-09-05 15:33:50.170802	1	ELETTORE	E
56	2022-09-05 15:52:39.219969	1	ELETTORE	E
57	2022-09-05 15:57:51.210769	1	ELETTORE	E
58	2022-09-05 16:07:31.309612	1	ELETTORE	E
59	2022-09-05 22:27:26.630543	1	ELETTORE	E
60	2022-09-05 22:29:27.380741	1	ELETTORE	E
61	2022-09-06 11:16:21.360532	1	ELETTORE	E
62	2022-09-06 11:47:50.603792	1	ELETTORE	E
63	2022-09-06 12:33:39.982468	1	ELETTORE	E
64	2022-09-06 12:34:45.615649	1	ELETTORE	E
65	2022-09-06 13:18:23.870985	1	ELETTORE	E
66	2022-09-06 13:21:12.556029	1	ELETTORE	E
67	2022-09-06 13:39:24.926199	1	ELETTORE	E
68	2022-09-06 14:07:20.638462	1	ELETTORE	E
69	2022-09-06 15:02:05.437239	1	ELETTORE	E
70	2022-09-06 15:04:26.949471	1	ELETTORE	E
71	2022-09-06 15:06:06.986608	1	ELETTORE	E
72	2022-09-06 15:08:47.45182	1	ELETTORE	E
73	2022-09-06 15:20:38.151567	1	ELETTORE	E
74	2022-09-06 15:21:25.804504	1	ELETTORE	E
75	2022-09-06 15:32:10.694854	1	ELETTORE	E
76	2022-09-06 15:33:18.447801	1	ELETTORE	E
77	2022-09-06 15:33:38.778776	1	ELETTORE	E
78	2022-09-06 15:34:40.686575	1	ELETTORE	E
79	2022-09-06 15:38:25.961295	1	ELETTORE	E
80	2022-09-06 15:39:03.617267	1	ELETTORE	E
81	2022-09-06 15:39:17.611383	1	ELETTORE	E
82	2022-09-06 15:39:47.447468	1	ELETTORE	E
83	2022-09-06 15:43:20.284816	1	ELETTORE	E
84	2022-09-06 15:44:06.894367	1	ELETTORE	E
85	2022-09-06 15:45:53.832247	1	ELETTORE	E
86	2022-09-06 15:46:19.600871	1	ELETTORE	E
87	2022-09-06 15:58:06.469067	1	ELETTORE	E
88	2022-09-06 15:59:22.374202	1	ELETTORE	E
89	2022-09-06 16:00:40.594986	1	ELETTORE	E
90	2022-09-06 16:14:23.99463	1	ELETTORE	E
91	2022-09-06 16:16:33.824045	1	ELETTORE	E
92	2022-09-06 16:17:47.132883	1	ELETTORE	E
93	2022-09-06 16:18:13.91848	1	ELETTORE	E
94	2022-09-06 16:23:32.844342	1	ELETTORE	E
95	2022-09-06 16:31:40.071008	1	ELETTORE	E
96	2022-09-06 16:44:49.211872	1	ELETTORE	E
97	2022-09-06 16:58:50.238583	1	ELETTORE	E
98	2022-09-06 16:59:36.391593	1	ELETTORE	E
99	2022-09-06 17:04:28.527749	1	ELETTORE	E
100	2022-09-06 17:04:42.86087	1	ELETTORE	E
101	2022-09-06 17:08:21.791536	1	ELETTORE	E
102	2022-09-06 17:08:38.434697	1	ELETTORE	E
103	2022-09-06 17:14:54.554378	1	ELETTORE	E
104	2022-09-06 18:52:18.211223	1	ELETTORE	E
105	2022-09-06 18:57:03.301396	1	ELETTORE	E
106	2022-09-06 19:03:17.940921	1	ELETTORE	E
107	2022-09-06 19:03:56.833558	1	ELETTORE	E
108	2022-09-06 19:04:30.817645	1	ELETTORE	E
109	2022-09-06 19:04:51.252007	1	ELETTORE	E
110	2022-09-06 19:06:21.572701	1	ELETTORE	E
111	2022-09-06 19:06:35.701014	1	ELETTORE	E
112	2022-09-06 19:06:44.830205	1	ELETTORE	E
113	2022-09-06 19:07:36.948296	1	ELETTORE	E
114	2022-09-06 19:13:42.851395	1	ELETTORE	E
115	2022-09-07 10:12:46.289702	1	ELETTORE	E
116	2022-09-07 10:13:01.03866	1	ELETTORE	E
117	2022-09-07 10:14:18.515231	1	ELETTORE	E
118	2022-09-07 10:14:38.753508	1	ELETTORE	E
119	2022-09-07 10:15:56.393521	1	ELETTORE	E
120	2022-09-07 10:24:34.290805	1	ELETTORE	E
121	2022-09-07 10:26:15.213942	1	ELETTORE	E
122	2022-09-07 10:27:10.929556	1	ELETTORE	E
123	2022-09-07 10:28:21.372568	1	ELETTORE	E
124	2022-09-07 10:53:09.88239	1	ELETTORE	E
125	2022-09-07 12:10:59.079517	1	ELETTORE	E
126	2022-09-07 12:11:20.780761	2	ELETTORE	E
127	2022-09-07 12:11:56.503452	2	ELETTORE	E
128	2022-09-07 12:12:17.753021	2	ELETTORE	E
129	2022-09-07 14:50:43.611122	1	ELETTORE	E
130	2022-09-07 14:51:02.526323	2	ELETTORE	E
131	2022-09-07 15:58:13.480999	1	ELETTORE	E
132	2022-09-07 15:58:35.248605	1	ELETTORE	E
133	2022-09-07 16:18:02.777826	1	ELETTORE	E
134	2022-09-07 16:21:20.285201	1	ELETTORE	E
135	2022-09-07 16:25:39.522067	1	ELETTORE	E
136	2022-09-07 16:26:11.290023	1	ELETTORE	E
137	2022-09-07 16:26:54.785143	1	ELETTORE	E
138	2022-09-07 16:27:21.911255	2	ELETTORE	E
139	2022-09-07 16:32:58.655342	1	ELETTORE	E
140	2022-09-07 16:36:00.491881	1	ELETTORE	E
141	2022-09-07 16:37:39.812158	1	ELETTORE	E
142	2022-09-07 16:38:06.962623	1	ELETTORE	E
143	2022-09-07 18:59:07.253684	1	ELETTORE	E
144	2022-09-07 18:59:18.248548	2	ELETTORE	E
145	2022-09-07 22:49:36.779683	1	ELETTORE	E
146	2022-09-07 22:49:37.773361	5	ELETTORE	E
147	2022-09-07 22:49:40.186825	1	ELETTORE	E
148	2022-09-07 22:52:19.214479	1	ELETTORE	E
149	2022-09-07 22:52:20.077276	5	ELETTORE	E
150	2022-09-07 22:57:09.860181	1	ELETTORE	E
151	2022-09-07 22:57:11.087715	5	ELETTORE	E
152	2022-09-07 22:57:14.059664	1	ELETTORE	E
153	2022-09-07 22:57:30.214709	2	ELETTORE	E
154	2022-09-07 22:57:31.788697	5	ELETTORE	E
155	2022-09-08 11:02:02.146853	1	ELETTORE	E
156	2022-09-08 11:02:27.28047	2	ELETTORE	E
157	2022-09-08 11:02:33.803839	5	ELETTORE	E
158	2022-09-08 11:48:14.365944	1	ELETTORE	E
159	2022-09-08 11:48:29.859064	2	ELETTORE	E
160	2022-09-08 11:48:31.846255	5	ELETTORE	E
161	2022-09-08 11:56:26.367149	1	ELETTORE	E
162	2022-09-08 11:56:44.439653	2	ELETTORE	E
163	2022-09-08 11:56:50.701954	5	ELETTORE	E
164	2022-09-08 14:26:35.519937	1	ELETTORE	E
165	2022-09-08 14:27:47.428081	1	ELETTORE	E
166	2022-09-08 14:28:13.357555	1	ELETTORE	E
167	2022-09-08 14:33:07.765943	1	ELETTORE	E
168	2022-09-08 14:33:54.668635	1	ELETTORE	E
169	2022-09-08 14:37:34.090934	1	ELETTORE	E
170	2022-09-08 14:48:56.024645	1	ELETTORE	E
171	2022-09-08 14:52:30.078401	1	ELETTORE	E
172	2022-09-08 16:55:32.176188	1	ELETTORE	E
173	2022-09-08 17:05:46.881855	1	ELETTORE	E
174	2022-09-08 17:06:11.554637	1	ELETTORE	E
175	2022-09-08 17:06:55.835311	1	ELETTORE	E
176	2022-09-08 17:08:00.164741	1	ELETTORE	E
177	2022-09-08 17:12:22.283435	1	ELETTORE	E
178	2022-09-08 17:12:44.983533	1	ELETTORE	E
179	2022-09-08 17:13:08.019791	1	ELETTORE	E
180	2022-09-08 17:13:35.132936	1	ELETTORE	E
181	2022-09-08 17:23:49.570819	1	ELETTORE	E
182	2022-09-08 17:24:11.080378	1	ELETTORE	E
183	2022-09-08 17:37:32.495549	1	ELETTORE	E
184	2022-09-08 17:40:46.960919	1	ELETTORE	E
185	2022-09-08 17:43:01.095211	1	ELETTORE	E
186	2022-09-08 17:54:38.538754	1	ELETTORE	E
187	2022-09-08 18:08:33.009709	1	ELETTORE	E
188	2022-09-08 18:09:25.760997	1	ELETTORE	E
189	2022-09-08 18:10:22.187371	1	ELETTORE	E
190	2022-09-08 18:11:43.445974	1	ELETTORE	E
191	2022-09-08 18:12:13.787441	1	ELETTORE	E
192	2022-09-08 18:13:39.43087	1	ELETTORE	E
193	2022-09-08 18:14:05.248294	1	ELETTORE	E
194	2022-09-08 18:17:51.663184	1	ELETTORE	E
195	2022-09-08 18:18:51.663307	1	ELETTORE	E
196	2022-09-08 21:29:07.822906	1	ELETTORE	E
197	2022-09-08 21:32:25.316473	1	ELETTORE	E
198	2022-09-08 21:36:54.892786	1	ELETTORE	E
199	2022-09-08 21:37:28.879226	1	ELETTORE	E
200	2022-09-08 21:38:06.889808	1	ELETTORE	E
201	2022-09-08 21:38:26.688886	1	ELETTORE	E
202	2022-09-08 21:40:40.361565	1	ELETTORE	E
203	2022-09-08 21:41:51.130283	1	ELETTORE	E
204	2022-09-08 21:44:03.485841	1	ELETTORE	E
205	2022-09-08 21:45:41.4268	1	ELETTORE	E
206	2022-09-08 21:48:05.295116	1	ELETTORE	E
207	2022-09-08 21:50:11.669755	1	ELETTORE	E
208	2022-09-08 21:52:38.101996	1	ELETTORE	E
209	2022-09-08 21:56:24.526599	1	ELETTORE	E
210	2022-09-08 21:59:57.232803	1	ELETTORE	E
211	2022-09-08 22:01:40.324685	1	ELETTORE	E
212	2022-09-08 22:05:45.805474	1	ELETTORE	E
213	2022-09-08 22:07:11.132205	1	ELETTORE	E
214	2022-09-08 22:08:58.085861	1	ELETTORE	E
215	2022-09-08 22:09:27.797514	1	ELETTORE	E
216	2022-09-08 22:24:28.830733	1	ELETTORE	E
217	2022-09-08 22:26:39.697327	1	ELETTORE	E
218	2022-09-08 22:27:54.900532	1	ELETTORE	E
219	2022-09-08 22:32:18.338563	1	ELETTORE	E
220	2022-09-08 22:33:52.388291	1	ELETTORE	E
221	2022-09-08 22:41:28.817472	1	ELETTORE	E
222	2022-09-08 22:43:50.760489	1	ELETTORE	E
223	2022-09-08 22:44:24.300326	1	ELETTORE	E
224	2022-09-08 23:09:42.074972	1	ELETTORE	E
225	2022-09-08 23:11:21.371778	1	ELETTORE	E
\.


--
-- Data for Name: Azioni_auditing; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Azioni_auditing" (id, azione) FROM stdin;
1	LOGIN
2	VOTAZIONE
3	APERTURA_SESSIONE
4	CHIUSURA_SESSIONE
5	LOGOUT
\.


--
-- Data for Name: Candidati; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Candidati" (id, persona, partito, ruolo) FROM stdin;
1	MTTSVN72C07F205A	2	TikToker
2	MRCPSN98H11A794G	1	Segretario
3	MSSSTN81P06F839X	3	Boia
4	LNTVTT77T42B157Z	1	Presidente
\.


--
-- Data for Name: Elettori; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Elettori" (cf, password, diritto_voto, diritto_voto_distanza) FROM stdin;
TLLMRA99L13H2640	$2a$10$fKo1OxDBeksu4x2acSae4u3JLP2FJ7VDA5DFBhukI1tY3fVHIDtRq	t	f
E	$2a$10$DFq3nF5F3rM4klEcRS3SC.nk.L1KLltWVkTdxktkG3Jexe82Ce/v6	t	f
\.


--
-- Data for Name: Gestori; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Gestori" (cf, password) FROM stdin;
RBNSRA92R50L113H	$2a$10$.PIxbqfYnUCx9oxrD4A0.eLBMytou4oYkbIqmlChlUEau89BkIYQu
TLLMRA99L13H2640	$2a$10$YQnGQVRzCurbwcbavzZgmOBSaiKU7fqWYiDXybBsUhsnGjJ41a6GC
E	$2a$10$f/ZMUkX7FpP5NEDJI03uD.AT/DzxbdolDOfNpSiQf.esOIEYmrIs6
\.


--
-- Data for Name: Partiti; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Partiti" (id, nome) FROM stdin;
1	Partito pirata
2	Lega
3	Casa pound
\.


--
-- Data for Name: Persone; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Persone" (cf, nome, cognome, sesso, nazionalita, luogo_nascita, data_nascita) FROM stdin;
TLLMRA99L13H2640	Mauro	Tellaroli	M	ITA	Rho	1999-07-13
RBNSRA92R50L113H	Sara	Rubini	F	ITA	Termoli	1992-10-10
E	Gran	Elettore	F	ITA	Milano	1990-09-08
MTTSVN72C07F205A	Matteo	Salvini	M	ITA	Milano	1972-03-07
MRCPSN98H11A794G	Marco	Pasini	M	ITA	Bergamo	1998-06-11
MSSSTN81P06F839X	Massimo	Santini	M	ITA	Napoli	1981-09-06
LNTVTT77T42B157Z	Violetta	Lonati	F	ITA	Brescia	1977-12-02
\.


--
-- Data for Name: Referendum; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Referendum" (sessione, quesito, si, no) FROM stdin;
3	Vuoi che si elimini dalla faccia della terra il reddito di cittadinanza?	0	0
2	Vuoi che il nord diventi indipendente?	0	0
1	Vuoi rendere legale l'eutanasia in Italia? Quindi se ti vuoi suicidare lo fai visto che la vita fa schifo soprattutto se fai UNIMI informatica e hai deciso di entrare in un girone dell'inferno	2	0
\.


--
-- Data for Name: Sessioni; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."Sessioni" (id, titolo, data_apertura, data_chiusura, tipo_votazione, tipo_scrutinio, chiusa, gestore, "votazionePartiti") FROM stdin;
1	Eutanasia	2022-09-02	2022-09-04	REFERENDUM	REFERENDUM	f	RBNSRA92R50L113H	f
2	Indipendenza nord	2022-09-06	2022-09-16	REFERENDUM	REFERENDUM_QUORUM	f	TLLMRA99L13H2640	f
3	Reddito di cittadinanza	2022-09-13	2022-09-18	REFERENDUM	REFERENDUM	f	RBNSRA92R50L113H	f
4	Elezioni ordinale maggioranza	2022-09-13	2022-09-16	ORDINALE	MAGGIORANZA	f	TLLMRA99L13H2640	f
\.


--
-- Data for Name: VotiCandidati; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."VotiCandidati" (sessione, candidato, voti) FROM stdin;
4	1	0
4	4	0
4	3	0
4	2	0
\.


--
-- Data for Name: VotiElettori; Type: TABLE DATA; Schema: sve; Owner: postgres
--

COPY sve."VotiElettori" (elettore, sessione, orario) FROM stdin;
E	1	2022-09-08 11:56:44.436655
\.


--
-- Name: Auditing_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Auditing_id_seq"', 225, true);


--
-- Name: Azioni_auditing_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Azioni_auditing_id_seq"', 5, true);


--
-- Name: Candidati_int_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Candidati_int_seq"', 4, true);


--
-- Name: Partiti_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Partiti_id_seq"', 3, true);


--
-- Name: Sessione_id_seq; Type: SEQUENCE SET; Schema: sve; Owner: postgres
--

SELECT pg_catalog.setval('sve."Sessione_id_seq"', 6, true);


--
-- Name: Auditing Auditing_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Auditing"
    ADD CONSTRAINT "Auditing_pkey" PRIMARY KEY (id);


--
-- Name: Candidati Candidati_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Candidati"
    ADD CONSTRAINT "Candidati_pkey" PRIMARY KEY (id);


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
-- Name: Partiti Partiti_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Partiti"
    ADD CONSTRAINT "Partiti_pkey" PRIMARY KEY (id);


--
-- Name: Persone Persona_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Persone"
    ADD CONSTRAINT "Persona_pkey" PRIMARY KEY (cf);


--
-- Name: Referendum Referendum_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Referendum"
    ADD CONSTRAINT "Referendum_pkey" PRIMARY KEY (sessione);


--
-- Name: Sessioni Sessione_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni"
    ADD CONSTRAINT "Sessione_pkey" PRIMARY KEY (id);


--
-- Name: VotiElettori VotiElettori_pkey; Type: CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."VotiElettori"
    ADD CONSTRAINT "VotiElettori_pkey" PRIMARY KEY (elettore, sessione);


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
-- Name: Candidati Candidati_partito_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Candidati"
    ADD CONSTRAINT "Candidati_partito_fkey" FOREIGN KEY (partito) REFERENCES sve."Partiti"(id);


--
-- Name: Candidati Candidati_persona_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Candidati"
    ADD CONSTRAINT "Candidati_persona_fkey" FOREIGN KEY (persona) REFERENCES sve."Persone"(cf);


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
-- Name: Referendum Referendum_sessione_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Referendum"
    ADD CONSTRAINT "Referendum_sessione_fkey" FOREIGN KEY (sessione) REFERENCES sve."Sessioni"(id);


--
-- Name: Sessioni Sessione_gestore_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."Sessioni"
    ADD CONSTRAINT "Sessione_gestore_fkey" FOREIGN KEY (gestore) REFERENCES sve."Gestori"(cf);


--
-- Name: VotiCandidati VotiCandidati_candidato_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."VotiCandidati"
    ADD CONSTRAINT "VotiCandidati_candidato_fkey" FOREIGN KEY (candidato) REFERENCES sve."Candidati"(id);


--
-- Name: VotiCandidati VotiCandidati_sessione_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."VotiCandidati"
    ADD CONSTRAINT "VotiCandidati_sessione_fkey" FOREIGN KEY (sessione) REFERENCES sve."Sessioni"(id);


--
-- Name: VotiElettori VotiElettori_elettore_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."VotiElettori"
    ADD CONSTRAINT "VotiElettori_elettore_fkey" FOREIGN KEY (elettore) REFERENCES sve."Elettori"(cf);


--
-- Name: VotiElettori VotiElettori_sessione_fkey; Type: FK CONSTRAINT; Schema: sve; Owner: postgres
--

ALTER TABLE ONLY sve."VotiElettori"
    ADD CONSTRAINT "VotiElettori_sessione_fkey" FOREIGN KEY (sessione) REFERENCES sve."Sessioni"(id);


--
-- PostgreSQL database dump complete
--

