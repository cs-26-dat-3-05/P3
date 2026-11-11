package com.place2book.internal.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseStatus;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;


@Repository
public class CaseRepository {
    private static final String COLUMNS = """
            id, organizer_id, transaction_id, status, event_id,
            rejected_registration_number, rejected_account_number, amount, created_at""";

    private final JdbcClient jdbc;

    CaseRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Finds cases and returns newest first
     *
     * @param status      only cases with this status, or null for all
     * @param organizerId only cases for this organizer, or null for all
     */
    public List<Case> findAll(CaseStatus status, String organizerId) {
        StringBuilder sql = new StringBuilder("SELECT " + COLUMNS + " FROM cases WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (status != null) {
            sql.append(" AND status = :status");
            params.put("status", status.name());
        }
        if (organizerId != null) {
            sql.append(" AND organizer_id = :organizerId");
            params.put("organizerId", organizerId);
        }
        sql.append(" ORDER BY created_at DESC, id DESC");

        return jdbc.sql(sql.toString())
            .params(params)
            .query(Case.class)
            .list();
    }

    public Optional<Case> findById(long id) {
        return jdbc.sql("SELECT " + COLUMNS + " FROM cases WHERE id = ?")
            .param(id)
            .query(Case.class)
            .optional();
    }

    /**
     * Inserts the case and returns it with the id and createdAt set by the database.
     */
    public Case insert(Case newCase) {
        return jdbc.sql("""
                INSERT INTO cases (organizer_id, transaction_id, status, event_id,
                                   rejected_registration_number, rejected_account_number, amount)
                VALUES (:organizerId, :transactionId, :status, :eventId,
                        :rejectedRegistrationNumber, :rejectedAccountNumber, :amount)
                """ + "RETURNING " + COLUMNS)
            .param("organizerId", newCase.getOrganizerId())
            .param("transactionId", newCase.getTransactionId())
            .param("status", newCase.getStatus().name())
            .param("eventId", newCase.getEventId())
            .param("rejectedRegistrationNumber", newCase.getRejectedRegistrationNumber())
            .param("rejectedAccountNumber", newCase.getRejectedAccountNumber())
            .param("amount", newCase.getAmount())
            .query(Case.class)
            .single();
    }

    /**
     * @return true if the case exists and was updated
     */
    public boolean updateStatus(long id, CaseStatus status) {
        return jdbc.sql("UPDATE cases SET status = ? WHERE id = ?")
            .param(status.name())
            .param(id)
            .update() == 1;
    }
}
