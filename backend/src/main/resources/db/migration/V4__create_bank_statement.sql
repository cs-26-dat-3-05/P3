-- Bank Statement Table. Made a foreign key column in transaction table, so that each transaction can map to its corresponding bank statement --
-- Transaction id column get changes to an identity column --
-- Also started the id in transaction id from max + 1 to make sure the next id starts after the last one created --

CREATE TABLE bank_statement(
    id              BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created_at      TIMESTAMP   NOT NULL DEFAULT now()    
);

ALTER TABLE Transaction
    ADD COLUMN bank_statement_id BIGINT NULL REFERENCES bank_statement(id);

ALTER TABLE Transaction
    ALTER COLUMN TransactionID ADD GENERATED ALWAYS AS IDENTITY;

SELECT setval(pg_get_serial_sequence('transaction', 'transactionid'), COALESCE(MAX(TransactionID), 0) + 1, false) FROM Transaction;