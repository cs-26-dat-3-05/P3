package com.place2book.internal.service;

import java.util.List;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.Note;
import com.place2book.internal.repository.CaseRepository;
import com.place2book.internal.repository.NoteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final CaseRepository caseRepository;

    NoteService(NoteRepository noteRepository, CaseRepository caseRepository) {
        this.noteRepository = noteRepository;
        this.caseRepository = caseRepository;
    }

    /**
     * @return the notes of the case with the newest first
     * @throws NotFoundException if the case does not exist
     */
    public List<Note> findByCaseId(long caseId) {
        requireCase(caseId);
        return noteRepository.findByCaseId(caseId);
    }

    /**
     * Adds a note to a case.
     *
     * @param caseId    the case the note belongs to
     * @param text      the content of the note
     * @param createdBy who wrote the note
     * @return the created note with its id and createdAt
     * @throws NotFoundException if the case does not exist
     */
    @Transactional
    public Note create(long caseId, String text, String createdBy) {
        requireCase(caseId);

        Note note = new Note();
        note.setCaseId(caseId);
        note.setText(text.strip());
        note.setCreatedBy(createdBy);
        return noteRepository.insert(note);
    }

    /**
     * @throws NotFoundException if the note does not exist
     */
    public void delete(long id) {
        if (!noteRepository.deleteById(id)) {
            throw new NotFoundException("Note with id " + id + " not found");
        }
    }

    private void requireCase(long caseId) {
        if (caseRepository.findById(caseId).isEmpty()) {
            throw new NotFoundException("Case with id " + caseId + " not found");
        }
    }
}
