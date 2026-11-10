package com.place2book.internal.repository;

import java.util.List;

import com.place2book.internal.model.CaseLog;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


@Repository
public class CaseLogRepository {
    private static final String COLUMNS = "id, case_id, status, performed_by, created_at";

    private final JdbcClient jdbc;

    CaseLogRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Inserts the entry and returns it with the id and createdAt attributes
     */
    public CaseLog insert(CaseLog entry) {
        return jdbc.sql("""
                INSERT INTO case_log (case_id, status, performed_by)
                VALUES (:caseId, :status, :performedBy)
                """ + "RETURNING " + COLUMNS)
            .param("caseId", entry.getCaseId())
            .param("status", entry.getStatus().name())
            .param("performedBy", entry.getPerformedBy())
            .query(CaseLog.class)
            .single();
    }

    /**
     * Finds the history of a case and returns the oldest entry first.
     */
    public List<CaseLog> findByCaseId(long caseId) {
        return jdbc.sql("SELECT " + COLUMNS + " FROM case_log WHERE case_id = ? ORDER BY created_at, id")
            .param(caseId)
            .query(CaseLog.class)
            .list();
    }
}
