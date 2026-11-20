package com.place2book.internal.repository;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.model.Note;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


@Repository
public class NoteRepository {
    private static final String COLUMNS = "id, case_id, text, created_by, created_at";

    private final JdbcClient jdbc;

    NoteRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Returns newest first
     */
    public List<Note> findByCaseId(long caseId) {
        return jdbc.sql("SELECT " + COLUMNS + " FROM notes WHERE case_id = ? ORDER BY created_at DESC, id DESC")
            .param(caseId)
            .query(Note.class)
            .list();
    }

    public Optional<Note> findById(long id) {
        return jdbc.sql("SELECT " + COLUMNS + " FROM notes WHERE id = ?")
            .param(id)
            .query(Note.class)
            .optional();
    }


    public Note insert(Note note) {
        return jdbc.sql("""
                INSERT INTO notes (case_id, text, created_by)
                VALUES (:caseId, :text, :createdBy)
                """ + "RETURNING " + COLUMNS)
            .param("caseId", note.getCaseId())
            .param("text", note.getText())
            .param("createdBy", note.getCreatedBy())
            .query(Note.class)
            .single();
    }

    
    public boolean deleteById(long id) {
        return jdbc.sql("DELETE FROM notes WHERE id = ?")
            .param(id)
            .update() == 1;
    }
}
