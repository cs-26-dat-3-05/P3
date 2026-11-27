package com.place2book.internal.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

// Own code with model and repo
import com.place2book.internal.model.Transaction;
import com.place2book.internal.repository.BankStatementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class BankStatementRepositoryIT{

    @Autowired
    BankStatementRepository bankStatementRepository;

    @Autowired
    JdbcClient jdbc;

    long statementId;

    @BeforeEach
    void createBankStatement(){
        statementId = jdbc.sql("INSERT INTO bank_statement DEFAULT VALUES RETURNING id")
        .query(Long.class)
        .single();
    }
    // Insert test for bankstatement
    @Test
    void insertBankstatementId(){
        long id = bankStatementRepository.insertBankStatement();
        assertThat(id).isPositive();
    }

    // Insert test for transaction
    @Test
    void insertTransactionId(){
        long id = bankStatementRepository.insertTransaction(transaction("01M8D6E1QFAZMV8VK046N23VCH",statementId));
        assertThat(id).isPositive();

        Long storedStatementId = jdbc.sql("SELECT bank_statement_id FROM TRANSACTION WHERE TransactionID = ?")
            .param(id)
            .query(Long.class)
            .single();
        assertThat(storedStatementId).isEqualTo(statementId);
    }

    // Test for that our method for finding the ulid thats been inserted is true
    @Test
    void existsByUlidAfterInsert(){
        String ulid = "01M8DBGYDTYS8PBXTG1XPKR4FA";
        bankStatementRepository.insertTransaction(transaction(ulid, statementId));

        assertThat(bankStatementRepository.existsByUlid(ulid)).isTrue();
        assertThat(bankStatementRepository.existsByUlid("00000000000000000000000000")).isFalse();
    }

    // Test for that a dupe gives a DataIntegrityViolationException class. So we insert the same ulid two times
    @Test
    void duplicateUlidIsRejected(){
        String ulid = "01M8DC18Z7HB6GQTQV3RHA2B5M";
        bankStatementRepository.insertTransaction(transaction(ulid, statementId));

        assertThatThrownBy(() -> bankStatementRepository.insertTransaction(transaction(ulid, statementId)))
        .isInstanceOf(DataIntegrityViolationException.class);
    }

    // Test for a transaction pointing at a bank statement that doesnt exist
    @Test
    void unknownBankStatementIsRejected(){
        String ulid = "01M8DCXSQB3123YKXY1TT72KD0";

    assertThatThrownBy(() -> bankStatementRepository.insertTransaction(transaction(ulid, -1)))
    .isInstanceOf(DataIntegrityViolationException.class);
    }



    static Transaction transaction(String ulid, long bankStatementId){
        Transaction tx = new Transaction();
        tx.setBankStatementId(bankStatementId);
        tx.setUlid(ulid);
        tx.setBookingDate(LocalDate.of(2026,8,30));
        tx.setAmount( new BigDecimal("100.00"));
        tx.setCurrency("DKK");
        tx.setRemittanceText("Retur af udbetaling");

        return tx;
    }


}

