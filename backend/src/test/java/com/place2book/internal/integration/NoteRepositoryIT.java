package com.place2book.internal.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.model.Note;
import com.place2book.internal.repository.CaseRepository;
import com.place2book.internal.repository.NoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

/**
 * Every test runs in a transaction that is rolled back afterwards.
 */
@SpringBootTest
@Transactional
class NoteRepositoryIT {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    JdbcClient jdbc;

    long caseId;
    long otherCaseId;

    @BeforeEach
    void insertCases() {
        jdbc.sql("""
                INSERT INTO Organizer (OrganizerID, Name, Email, RegistrationNumber, AccountNumber)
                VALUES ('it-note', 'IT organizer', 'note@example.com', '1234', '1234567890')
                """).update();
        jdbc.sql("""
                INSERT INTO Transaction (TransactionID, ULID, BookingDate, Amount, Currency, CreatedAt)
                VALUES (900002, '01ITNOTEITNOTEITNOTEITNOTE', '2026-11-01', 100.00, 'DKK', '2026-11-01 09:00:00')
                """).update();
        caseId = insertCase().getId();
        otherCaseId = insertCase().getId();
    }

    Case insertCase() {
        Case c = new Case();
        c.setOrganizerId("it-note");
        c.setTransactionId(900002L);
        c.setStatus(CaseStatus.REGISTERED);
        c.setEventId("event");
        c.setRejectedRegistrationNumber("1234");
        c.setRejectedAccountNumber("1234567890");
        c.setAmount(new BigDecimal("100.00"));
        return caseRepository.insert(c);
    }

    @Test
    void insertReturnsGeneratedIdAndCreatedAt() {
        Note inserted = noteRepository.insert(note(caseId, "Ringet til arrangøren"));

        assertThat(inserted.getId()).isNotNull();
        assertThat(inserted.getCreatedAt()).isNotNull();
        assertThat(inserted.getCaseId()).isEqualTo(caseId);
        assertThat(inserted.getText()).isEqualTo("Ringet til arrangøren");
        assertThat(inserted.getCreatedBy()).isEqualTo("TESTUSER");
    }

    @Test
    void findById() {
        Note inserted = noteRepository.insert(note(caseId, "Hej"));

        assertThat(noteRepository.findById(inserted.getId()))
            .get()
            .extracting(Note::getText)
            .isEqualTo("Hej");
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        assertThat(noteRepository.findById(-1)).isEmpty();
    }

    @Test
    void findByCaseIdReturnsOnlyNotesOfTheCaseNewestFirst() {
        Note first = noteRepository.insert(note(caseId, "Første"));
        Note second = noteRepository.insert(note(caseId, "Anden"));
        noteRepository.insert(note(otherCaseId, "Anden sag"));


        List<Note> notes = noteRepository.findByCaseId(caseId);

        assertThat(notes).extracting(Note::getId).containsExactly(first.getId(), second.getId());
    }

    @Test
    void findByCaseIdIsEmptyForUnknownCase() {
        assertThat(noteRepository.findByCaseId(-1)).isEmpty();
    }

    @Test
    void deleteById() {
        Note inserted = noteRepository.insert(note(caseId, "Slet mig"));

        assertThat(noteRepository.deleteById(inserted.getId())).isTrue();
        assertThat(noteRepository.findById(inserted.getId())).isEmpty();
    }

    @Test
    void deleteByIdReturnsFalseForUnknownId() {
        assertThat(noteRepository.deleteById(-1)).isFalse();
    }

    @Test
    void insertForUnknownCaseIsRejectedByTheDatabase() {
        assertThatThrownBy(() -> noteRepository.insert(note(-1, "Ingen sag")))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    static Note note(long caseId, String text) {
        Note note = new Note();
        note.setCaseId(caseId);
        note.setText(text);
        note.setCreatedBy("TESTUSER");
        return note;
    }
}
