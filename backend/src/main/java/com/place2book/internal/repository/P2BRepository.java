package com.place2book.internal.repository;

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
    
    public Organizer findOrganizerById(int id) {
        return jdbc.sql("SELECT id, name, cvr, email, phone FROM organizer WHERE id = ?")
            .param(id)
            .query(Organizer.class)
            .single();
    }
}
