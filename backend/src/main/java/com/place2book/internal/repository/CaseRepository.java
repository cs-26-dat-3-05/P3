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
    // Case columns + organizer name. c = cases, o = organizer
    private static final String SELECT_WITH_ORGANIZER = """
            SELECT c.id, c.organizer_id, c.transaction_id, c.status, c.event_id,
                   c.rejected_registration_number, c.rejected_account_number,
                   c.amount, c.created_at, c.requires_action,
                   o.name AS organizer_name
            FROM cases c
            LEFT JOIN organizer o ON o.organizerid = c.organizer_id
            """;

    private final JdbcClient jdbc;

    CaseRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Case> findAll(CaseStatus status, String organizerId) {
        StringBuilder sql = new StringBuilder(SELECT_WITH_ORGANIZER + " WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (status != null) {
            sql.append(" AND c.status = :status");
            params.put("status", status.name());
        }
        if (organizerId != null) {
            sql.append(" AND c.organizer_id = :organizerId");
            params.put("organizerId", organizerId);
        }
        sql.append(" ORDER BY c.created_at DESC, c.id DESC");

        return jdbc.sql(sql.toString())
                .params(params)
                .query(Case.class)
                .list();
    }

    public Optional<Case> findById(long id) {
        return jdbc.sql(SELECT_WITH_ORGANIZER + " WHERE c.id = ?")
                .param(id)
                .query(Case.class)
                .optional();
    }

    /**
     * Inserts the case and returns it with id, createdAt and organizerName set.
     */
    public Case insert(Case newCase) {
        long id = jdbc.sql("""
                INSERT INTO cases (organizer_id, transaction_id, status, event_id,
                                   rejected_registration_number, rejected_account_number, amount)
                VALUES (:organizerId, :transactionId, :status, :eventId,
                        :rejectedRegistrationNumber, :rejectedAccountNumber, :amount)
                RETURNING id
                """)
                .param("organizerId", newCase.getOrganizerId())
                .param("transactionId", newCase.getTransactionId())
                .param("status", newCase.getStatus().name())
                .param("eventId", newCase.getEventId())
                .param("rejectedRegistrationNumber", newCase.getRejectedRegistrationNumber())
                .param("rejectedAccountNumber", newCase.getRejectedAccountNumber())
                .param("amount", newCase.getAmount())
                .query(Long.class)
                .single();

        return findById(id).orElseThrow();
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
