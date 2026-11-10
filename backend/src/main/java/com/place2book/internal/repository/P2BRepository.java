package com.place2book.internal.repository;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.model.BankAccount;
import com.place2book.internal.model.Event;
import com.place2book.internal.model.Organizer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class P2BRepository {
    private JdbcClient jdbc;

    P2BRepository(@Qualifier("place2book") JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Organizer> findOrganizerById(long id) {
        return jdbc.sql("SELECT id, name, cvr, email, phone FROM organizer WHERE id = ?")
                .param(id)
                .query(Organizer.class)
                .optional();
    }

    public List<BankAccount> findBankAccountsByOrganizerId(long organizerId) {
        return jdbc.sql(
                "SELECT id, organizer_id, accprefix, accmain, event_default, active FROM bank_account WHERE organizer_id = ?")
                .param(organizerId)
                .query(BankAccount.class)
                .list();
    }

    public List<Event> findEventsByOrganizerId(long organizerId) {
        return jdbc.sql(
                "SELECT id, bank_account_id, name, organizer_id FROM event WHERE organizer_id = ?")
                .param(organizerId)
                .query(Event.class)
                .list();
    }
}
