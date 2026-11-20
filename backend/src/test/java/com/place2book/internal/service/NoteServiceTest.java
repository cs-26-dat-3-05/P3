package com.place2book.internal.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.Note;
import com.place2book.internal.repository.CaseRepository;
import com.place2book.internal.repository.NoteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    NoteRepository noteRepository;

    @Mock
    CaseRepository caseRepository;

    NoteService service;

    @BeforeEach
    void setUp() {
        service = new NoteService(noteRepository, caseRepository);
    }

    void caseExists(long id) {
        Case c = new Case();
        c.setId(id);
        when(caseRepository.findById(id)).thenReturn(Optional.of(c));
    }

    @Test
    @DisplayName("findByCaseId returns the notes of the case")
    void findByCaseIdReturnsNotes() {
        caseExists(1);
        Note note = new Note(3L, 1L, "Hej", "TESTUSER", null);
        when(noteRepository.findByCaseId(1)).thenReturn(List.of(note));

        assertThat(service.findByCaseId(1)).containsExactly(note);
    }

    @Test
    @DisplayName("findByCaseId throws NotFoundException for an unknown case")
    void findByCaseIdThrowsForUnknownCase() {
        when(caseRepository.findById(42)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findByCaseId(42));
        assertThat(ex.getMessage()).isEqualTo("Case with id 42 not found");
        verify(noteRepository, never()).findByCaseId(anyLong());
    }

    @Test
    @DisplayName("create inserts the note on the case with the given author")
    void createInsertsNote() {
        caseExists(1);
        when(noteRepository.insert(any())).thenAnswer(invocation -> {
            Note inserted = invocation.getArgument(0);
            inserted.setId(9L);
            return inserted;
        });

        Note created = service.create(1, "Ringet til arrangøren", "TESTUSER");

        assertThat(created.getId()).isEqualTo(9L);

        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepository).insert(captor.capture());
        assertThat(captor.getValue().getCaseId()).isEqualTo(1L);
        assertThat(captor.getValue().getText()).isEqualTo("Ringet til arrangøren");
        assertThat(captor.getValue().getCreatedBy()).isEqualTo("TESTUSER");
    }

    @Test
    @DisplayName("create strips surrounding whitespace from the text")
    void createStripsText() {
        caseExists(1);
        when(noteRepository.insert(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.create(1, "  Hej  \n", "TESTUSER").getText()).isEqualTo("Hej");
    }

    @Test
    @DisplayName("create cannot add a note to a case that does not exist")
    void createThrowsForUnknownCase() {
        when(caseRepository.findById(42)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> service.create(42, "Hej", "TESTUSER"));
        assertThat(ex.getMessage()).isEqualTo("Case with id 42 not found");
        verify(noteRepository, never()).insert(any());
    }

    @Test
    @DisplayName("delete removes the note")
    void deleteRemovesNote() {
        when(noteRepository.deleteById(3)).thenReturn(true);

        service.delete(3);

        verify(noteRepository).deleteById(3);
    }

    @Test
    @DisplayName("delete throws NotFoundException for an unknown note")
    void deleteThrowsForUnknownNote() {
        when(noteRepository.deleteById(42)).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.delete(42));
        assertThat(ex.getMessage()).isEqualTo("Note with id 42 not found");
    }
}
