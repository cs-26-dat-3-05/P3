package com.place2book.internal.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseLog;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.repository.CaseLogRepository;
import com.place2book.internal.repository.CaseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

/**
 * Every test runs in a transaction that is rolled back afterwards.
 */
@SpringBootTest
@Transactional
class CaseRepositoryIT {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    CaseLogRepository caseLogRepository;

    @Autowired
    JdbcClient jdbc;

    @BeforeEach
    void insertOrganizersAndTransaction() {
        jdbc.sql("""
                INSERT INTO Organizer (OrganizerID, Name, Email, RegistrationNumber, AccountNumber) VALUES
                    ('it-1', 'IT organizer 1', 'one@example.com', '1234', '1234567890'),
                    ('it-2', 'IT organizer 2', 'two@example.com', '1234', '1234567890')
                """).update();
        jdbc.sql("""
                INSERT INTO Transaction (TransactionID, ULID, BookingDate, Amount, Currency, CreatedAt)
                VALUES (900001, '01ITTESTITTESTITTESTITTEST', '2026-11-01', 100.00, 'DKK', '2026-11-01 09:00:00')
                """).update();
    }

    Case insertCase(String organizerId, CaseStatus status) {
        Case c = new Case();
        c.setOrganizerId(organizerId);
        c.setTransactionId(900001L);
        c.setStatus(status);
        c.setEventId("event");
        c.setRejectedRegistrationNumber("1234");
        c.setRejectedAccountNumber("1234567890");
        c.setAmount(new BigDecimal("100.00"));
        return caseRepository.insert(c);
    }

    @Test
    void insertReturnsGeneratedIdAndCreatedAt() {
        Case inserted = insertCase("it-1", CaseStatus.REGISTERED);

        assertThat(inserted.getId()).isNotNull();
        assertThat(inserted.getCreatedAt()).isNotNull();
        assertThat(inserted.getStatus()).isEqualTo(CaseStatus.REGISTERED);
        assertThat(inserted.getAmount()).isEqualByComparingTo("100.00");
    }

    @Test
    void findById() {
        Case inserted = insertCase("it-1", CaseStatus.REGISTERED);

        Case found = caseRepository.findById(inserted.getId()).orElseThrow();
        assertThat(found.getOrganizerId()).isEqualTo("it-1");
        assertThat(found.getTransactionId()).isEqualTo(900001L);
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        assertThat(caseRepository.findById(-1)).isEmpty();
    }

    @Test
    void findAllFiltersOnStatusAndOrganizer() {
        Case registered1 = insertCase("it-1", CaseStatus.REGISTERED);
        Case identified1 = insertCase("it-1", CaseStatus.ORGANIZER_IDENTIFIED);
        Case registered2 = insertCase("it-2", CaseStatus.REGISTERED);

        assertThat(ids(caseRepository.findAll(null, "it-1")))
            .containsExactlyInAnyOrder(registered1.getId(), identified1.getId());
        assertThat(ids(caseRepository.findAll(CaseStatus.REGISTERED, null)))
            .contains(registered1.getId(), registered2.getId())
            .doesNotContain(identified1.getId());
        assertThat(ids(caseRepository.findAll(CaseStatus.REGISTERED, "it-2")))
            .containsExactly(registered2.getId());
        assertThat(ids(caseRepository.findAll(null, null)))
            .contains(registered1.getId(), identified1.getId(), registered2.getId());
    }

    @Test
    void updateStatus() {
        Case inserted = insertCase("it-1", CaseStatus.REGISTERED);

        assertThat(caseRepository.updateStatus(inserted.getId(), CaseStatus.RETRANSFERRED)).isTrue();
        assertThat(caseRepository.findById(inserted.getId()).orElseThrow().getStatus())
            .isEqualTo(CaseStatus.RETRANSFERRED);
    }

    @Test
    void updateStatusReturnsFalseForUnknownId() {
        assertThat(caseRepository.updateStatus(-1, CaseStatus.RETRANSFERRED)).isFalse();
    }

    @Test
    void caseLogIsReturnedOldestFirst() {
        Case inserted = insertCase("it-1", CaseStatus.REGISTERED);
        caseLogRepository.insert(logEntry(inserted.getId(), CaseStatus.REGISTERED, "Anders"));
        caseLogRepository.insert(logEntry(inserted.getId(), CaseStatus.ORGANIZER_IDENTIFIED, "Anton"));

        List<CaseLog> history = caseLogRepository.findByCaseId(inserted.getId());

        assertThat(history).extracting(CaseLog::getStatus)
            .containsExactly(CaseStatus.REGISTERED, CaseStatus.ORGANIZER_IDENTIFIED);
        assertThat(history).extracting(CaseLog::getPerformedBy)
            .containsExactly("Anders", "Anton");
        assertThat(history).allSatisfy(entry -> {
            assertThat(entry.getId()).isNotNull();
            assertThat(entry.getCreatedAt()).isNotNull();
        });
    }

    @Test
    void caseLogIsEmptyForUnknownCase() {
        assertThat(caseLogRepository.findByCaseId(-1)).isEmpty();
    }

    static CaseLog logEntry(long caseId, CaseStatus status, String performedBy) {
        CaseLog entry = new CaseLog();
        entry.setCaseId(caseId);
        entry.setStatus(status);
        entry.setPerformedBy(performedBy);
        return entry;
    }

    static List<Long> ids(List<Case> cases) {
        return cases.stream().map(Case::getId).toList();
    }
}
