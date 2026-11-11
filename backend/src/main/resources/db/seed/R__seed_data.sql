TRUNCATE Transaction, Organizer, User_Table, cases, case_log, Retransfer, Note RESTART IDENTITY CASCADE;

INSERT INTO Transaction(TransactionID, ULID, BookingDate, Amount, Currency, RemittanceText, CreatedAt) VALUES
    (1, '01M6F3TCCKQXZW4M9TPK0VEHA1', '2026-09-30', 8372.85, 'DKK', 'Udbetaling Place2Book den 30. september 2026', '2026-09-30 09:00:00'),
    (2, '01M6F44A7A5N59T2XPX66T0HT9', '2026-10-16', 24256.22, 'SEK', 'Udbetaling Place2Book den 16. oktober 2026', '2026-10-16 09:00:00'),
    (3, '01M6F47ZAHJBHRE24HW0M0QSP1', '2026-11-01', 100000.22, 'DKK', 'Udbetaling Place2Book den 1. november 2026', '2026-11-01 09:00:00');

INSERT INTO Organizer(OrganizerID, Name, Email, Phone, RegistrationNumber, AccountNumber) VALUES
    ('1', 'Rekom Group a/s', 'info@rekom.dk', '45 33 29 03 00', '438205824291', '5802853291'),
    ('4', 'Svens lydgrotte', 'svenslydgrotte@gmail.com', '45 53 80 72 23', '138225826391', '9862850221'),
    ('7', 'Aalborg bibliotek', 'bibliotek@aalborg.dk', '45 99 31 43 00', '134225827398', '1462850921');

INSERT INTO User_Table(UserID, Name) VALUES
    (1, 'Anders'),
    (2, 'Anton'),
    (3, 'Christoffer');

-- Ids are generated, so the cases get id 1, 2 and 3 in this order
INSERT INTO cases(organizer_id, transaction_id, status, event_id, rejected_registration_number, rejected_account_number, amount, created_at) VALUES
    ('1', 1, 'BANK_DETAILS_REQUESTED', '1', '438205824291', '5802853291', 8372.85, '2026-10-01 09:00:00'),
    ('4', 2, 'ORGANIZER_IDENTIFIED', '2', '138225826391', '9862850221', 24256.22, '2026-10-17 09:00:00'),
    ('7', 3, 'REGISTERED', '3', '134225827398', '1462850921', 100000.22, '2026-11-02 09:00:00');

INSERT INTO case_log(case_id, status, performed_by, created_at) VALUES
    (1, 'REGISTERED', 'Anders', '2026-10-01 09:00:00'),
    (1, 'ORGANIZER_IDENTIFIED', 'Anders', '2026-10-01 10:00:00'),
    (1, 'BANK_DETAILS_REQUESTED', 'Anton', '2026-10-02 09:00:00'),
    (2, 'REGISTERED', 'Anton', '2026-10-17 09:00:00'),
    (2, 'ORGANIZER_IDENTIFIED', 'Anton', '2026-10-30 09:00:00'),
    (3, 'REGISTERED', NULL, '2026-11-02 09:00:00');

INSERT INTO Retransfer(RetransferID, CaseID, RegistrationNumber, AccountNumber, Amount, PaymentID, StatusCode, CreatedAt) VALUES
    (1, 1, '438205824291', '290249028', 8372.85,'PAY-20261024-0001', 'ACSC', '2026-10-20 09:00:00' ),
    (2,2, '138225826391', '190949528', 24256.22,'PAY-20261025-0002', 'PDNG', '2026-10-30 09:00:00');

INSERT INTO Note(NoteID, CaseID, UserID, Content, CreatedAt) VALUES
    (1, 1, 1, 'Følg op på Rekom, da de ikke svarer.', '2026-10-02 09:00:00'),
    (2, 1, 1, 'Rekom har åbenbart problemer med at skifte konto.', '2026-10-05 09:00:00'),
    (3, 1, 1, 'De er helt væk, men transaktionen er fuldført.', '2026-10-20 09:00:00'),
    (4, 2, 2, 'Jeg har tjekket op på Svens lydgrotte og der mangler bare at bekræftes.', '2026-10-20 09:00:00'),
    (5, 3, 2, '100000 skal sendes så vær lige ekstra forsigtig.', '2026-11-02 09:00:00');
