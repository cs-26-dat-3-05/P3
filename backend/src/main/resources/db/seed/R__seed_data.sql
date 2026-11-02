--Seed file, manually inserting data instead of backend to test it works--
-- Same order as in tables v1 db, because references must be stored first--
-- Order:
/*
    1.Transaction
    2.Organizer
    3.User_Table
    4.Cases
    5.Status
    6.Retransfer
    7.Note
*/

-- First all tables are emptied, given R__ file
TRUNCATE Transaction, Organizer, User_Table, Cases, Status, Retransfer, Note CASCADE;

INSERT INTO Transaction(TransactionID, ULID, BookingDate, Amount, Currency, RemittanceText, CreatedAt) VALUES
(1, '01M6F3TCCKQXZW4M9TPK0VEHA1','2026-09-30', 8372.85, 'DKK', 'Udbetaling Place2Book den 9. september 2026', '2026-09-30 09:00:00'),
(2, '01M6F44A7A5N59T2XPX66T0HT9', '2026-10-16', 24256.22, 'SEK', 'Udbetaling Place2Book den 16. oktober 2026', '2026-10-16 09:00:00'),
(3, '01M6F47ZAHJBHRE24HW0M0QSP1', '2026-11-01 ', 100000.22, 'DKK', 'Udbetaling Place2Book den 1. november 2026', '2026-11-01 09:00:00');

INSERT INTO Organizer(OrganizerID, Name, Email, Phone, RegistrationNumber, AccountNumber) VALUES
('1', 'Rekom Group a/s', 'info@rekom.dk', '45 33 29 03 00', '438205824291', '5802853291'),
('4', 'Svens lydgrotte', 'svenslydgrotte@gmail.com', '45 53 80 72 23', '138225826391', '9862850221'),
('7', 'Aalborg bibliotek', 'bibliotek@aalborg.dk', '45 99 31 43 00', '134225827398', '1462850921' );

INSERT INTO User_Table(UserID, Name) VALUES
(1, 'Mette'),
(2, 'Fredrik');

INSERT INTO Cases(CaseID, OrganizerID, TransactionID, Status, EventID, RejectedRegistrationNumber, RejectedAccountNumber, Amount, CreatedAt) VALUES
(1, '1', 1, 3, '1', '438205824291', '5802853291', 8372.85, '2026-10-01 09:00:00' ),
(2,'4', 2, 2, '2', '138225826391', '9862850221', 24256.22, '2026-10-17 09:00:00'),
(3, '7', 3, 1, '3', '134225827398', '1462850921', 100000.22, '2026-11-02 09:00:00');

INSERT INTO Status(StatusID, CaseID, UserID, State, CreatedAt) VALUES
(1,1,2,6, '2026-10-20 09:00:00'),
(2,2,1,3, '2026-10-30 09:00:00'),
(3,3,1,1, '2026-11-02 09:00:00');

INSERT INTO Retransfer(RetransferID, CaseID, RegistrationNumber, AccountNumber, Amount, PaymentID, StatusCode, CreatedAt) VALUES
(1,1, '438205824291', '290249028', 8372.85,'PAY-20261024-0001', 'ACSC', '2026-10-20 09:00:00' ),
(2,2, '138225826391', '190949528', 24256.22,'PAY-20261025-0002', 'PDNG', '2026-10-30 09:00:00');


INSERT INTO Note(NoteID, CaseID, UserID, Content, CreatedAt) VALUES
(1,1,1, 'Følg op på rekom, da de ikker svarer.', '2026-10-02 09:00:00'),
(2,1,1,'Rekom har åbenbart problemer med at skifte konto.', '2026-10-05 09:00:00'),
(3,1,1,'De er helt væk, men transaktionen er fuldført.', '2026-10-20 09:00:00'),
(4,2,2, 'Jeg har tjekket op på svens lydgrotte og der mangler bare at bekræftes.', '2026-10-20 09:00:00'),
(5,3,2, '100000 skal sendes så vær lige ekstra forsigtig', '2026-11-02 09:00:00');



