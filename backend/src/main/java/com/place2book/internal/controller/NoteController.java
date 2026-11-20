package com.place2book.internal.controller;

import java.util.List;

import com.place2book.internal.dto.CreateNoteRequest;
import com.place2book.internal.model.Note;
import com.place2book.internal.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Note", description = "Internal notes on cases")
public class NoteController {
    /**
     * TODO: Ændre til SSO user når det er konfigureret
     */

    static final String CURRENT_USER = "TESTUSER";

    private final NoteService noteService;

    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/cases/{caseId}/notes")
    @Operation(summary = "List the notes of a case", description = "Returns the newest notes first.")
    @ApiResponse(responseCode = "200", description = "The notes of the case")
    @ApiResponse(responseCode = "404", description = "Case not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public List<Note> findByCaseId(@PathVariable long caseId) {
        return noteService.findByCaseId(caseId);
    }

    @PostMapping("/cases/{caseId}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a note to a case",
            description = "TODO: The note is created by user \"TESTUSER\" until SSO login is added.")
    @ApiResponse(responseCode = "201", description = "The created note")
    @ApiResponse(responseCode = "400", description = "Missing, blank or too long text",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Case not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public Note create(@PathVariable long caseId, @Valid @RequestBody CreateNoteRequest request) {
        return noteService.create(caseId, request.text(), CURRENT_USER);
    }

    @DeleteMapping("/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a note")
    @ApiResponse(responseCode = "204", description = "The note was deleted")
    @ApiResponse(responseCode = "404", description = "Note not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public void delete(@PathVariable long id) {
        noteService.delete(id);
    }
}
