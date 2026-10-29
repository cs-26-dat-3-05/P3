-- Here the mock database is created and populated with some test data. This is used for testing purposes only.

-- Tables

-- Organizer table
CREATE TABLE organizer (
    id    SERIAL     PRIMARY KEY,
    name  TEXT       NOT NULL,
    cvr   VARCHAR(8) NOT NULL UNIQUE,
    email TEXT       NOT NULL,
    phone TEXT       NULL
);

-- BankAccount Table
CREATE TABLE bank_account (
    id            SERIAL PRIMARY KEY,
    organizer_id  INTEGER     NOT NULL REFERENCES organizer (id),
    accprefix     VARCHAR(4)  NOT NULL, -- Registreringsnummer
    accmain       VARCHAR(10) NOT NULL, -- Kontonummer
    event_default BOOLEAN     NOT NULL DEFAULT FALSE,
    active        BOOLEAN     NOT NULL DEFAULT TRUE
);

-- Event Table
CREATE TABLE event (
    id              SERIAL PRIMARY KEY,
    organizer_id    INTEGER NOT NULL REFERENCES organizer (id),
    bank_account_id INTEGER NOT NULL REFERENCES bank_account (id),
    name            TEXT    NOT NULL
);

-- Test data
INSERT INTO organizer (name, cvr, email, phone) VALUES
    ('Aalborg Jazz Festival', '12345678', 'info@aalborgjazzfestival.dk', '12345678'),
    ('Nordjysk Teaterforening', '87654321', 'kontakt@nordjyskteaterforening.dk', '87654321'),
    ('Musikhuset Vest', '11223344', 'info@musikhusetvest.dk', '11223344');

INSERT INTO bank_account (organizer_id, accprefix, accmain, event_default, active) VALUES
    (1, '9070', '1234567890', TRUE,  TRUE),
    (1, '9070', '0987654321', FALSE, TRUE),
    (2, '5301', '0000123456', TRUE,  TRUE),
    (3, '2211', '4455667788', TRUE,  FALSE);

INSERT INTO event (organizer_id, bank_account_id, name) VALUES
    (1, 1, 'Aalborg Jazz Festival 2026'),
    (1, 2, 'Jazz i Parken'),
    (2, 3, 'Hamlet'),
    (2, 3, 'Juleforestilling'),
    (3, 4, 'Lars Lilholt');

-- Read-only user for backend
CREATE ROLE p3_project_reader WITH LOGIN PASSWORD 'reader';
GRANT CONNECT ON DATABASE place2book TO p3_project_reader;
GRANT USAGE ON SCHEMA public TO p3_project_reader;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO p3_project_reader;
