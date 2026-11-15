package com.place2book.internal.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import com.place2book.internal.model.Organizer;
import com.place2book.internal.repository.P2BRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class P2BRepositoryIT {

    private final P2BRepository p2brepo;
    
    @Autowired
    P2BRepositoryIT(P2BRepository p2brepo) {
        this.p2brepo = p2brepo;
    }
    
    @Test
    void findOrganizerById() {
        Organizer organizer = p2brepo.findOrganizerById(1).orElseThrow();
        assertThat(organizer.getName()).isEqualTo("Aalborg Jazz Festival");
    }
    
    @Test
    void returnEmptyOptionalWhenIdDoesNotExist() {
        Organizer organizer = p2brepo.findOrganizerById(-1).orElse(new Organizer());
        assertThat(organizer.getName()).isEqualTo(null);
    }
    
    @Test
    void findOrganizerFromBankStatement() {
        /**
           PARENT TABLE
           Organizer id = 1:
           id |          name           |   cvr    |               email               |  phone
           ----+-------------------------+----------+-----------------------------------+----------
           1 | Aalborg Jazz Festival   | 12345678 | info@aalborgjazzfestival.dk | 12345678
           
           CHILD TABLES
           Bank accounts:
           id | organizer_id | accprefix |  accmain   | event_default | active
           ----+--------------+-----------+------------+---------------+--------
           1 |            1 | 9070      | 1234567890 | t             | t
           2 |            1 | 9070      | 0987654321 | f             | t
           
           Events:
           id | organizer_id | bank_account_id |            name            | amount
           ----+--------------+-----------------+----------------------------+---------
           1 |            1 |               1 | Aalborg Jazz Festival 2026 | 6767.67
           2 |            1 |               2 | Jazz i Parken              |   50.23
        **/   
        
        Organizer organizer = p2brepo.findOrganizerFromBankStatement(9070, 1234567890, new BigDecimal("6767.67")).orElseThrow();
        assertThat(organizer.getId()).isEqualTo(1);
        assertThat(organizer.getName()).isEqualTo("Aalborg Jazz Festival");
        assertThat(organizer.getEmail()).isEqualTo("info@aalborgjazzfestival.dk");
    }
    
    @Test
    void returnEmptyWhenOnlyOneChildMatches() {
        // bank account is right, but event amount does not match
        Optional<Organizer> organizer = p2brepo.findOrganizerFromBankStatement(9070, 1234567890, new BigDecimal("100.00"));
        assertThat(organizer.isEmpty());
    }
    
    @Test
    void returnEmptyOptionalNoChildMatches() {
        Optional<Organizer> organizer = p2brepo.findOrganizerFromBankStatement(-1, -1, new BigDecimal("-1"));
        assertThat(organizer.isEmpty());
    }
}

