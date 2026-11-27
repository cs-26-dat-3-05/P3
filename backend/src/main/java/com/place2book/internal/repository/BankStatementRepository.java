package com.place2book.internal.repository;
import com.place2book.internal.model.BankStatement;
import com.place2book.internal.model.Transaction;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BankStatementRepository{

    private final JdbcClient jdbc;

    BankStatementRepository (JdbcClient jdbc){
        this.jdbc = jdbc;
    }

    // Method for writing the bankstatement into sql and then getting the id back.
    // Single and long just means we expect a single number from the id in bankstatement table
    public long insertBankStatement(){
        return jdbc.sql("INSERT INTO bank_statement DEFAULT VALUES RETURNING id")
        .query(Long.class)
        .single();
    }

    // Since ulid is not generated (UNIQUE) in v1 we make a check if the row we insert has a ulid thats already in the table transaction
    public boolean existsByUlid(String ulid){
        return jdbc.sql("SELECT EXISTS (SELECT 1 FROM TRANSACTION WHERE ULID = ?)")
            .param(ulid)
            .query(Boolean.class)
            .single();
    }
    // Primary key stays out as its the PK
    // Now for createdAt 
    public long insertTransaction(Transaction tx){
        return jdbc.sql("""
            INSERT INTO Transaction (ULID,bank_statement_id, BookingDate, Amount, Currency, RemittanceText, CreatedAt)
            VALUES (:ulid, :bankStatementId, :bookingDate, :amount, :currency, :remittanceText, now())
            RETURNING TransactionID
        """)
        .param("ulid", tx.getUlid())
        .param("bankStatementId", tx.getBankStatementId())
        .param("bookingDate", tx.getBookingDate())
        .param("amount", tx.getAmount())
        .param("currency", tx.getCurrency())
        .param("remittanceText", tx.getRemittanceText())
        .query(Long.class)
        .single();
        
    }

}
