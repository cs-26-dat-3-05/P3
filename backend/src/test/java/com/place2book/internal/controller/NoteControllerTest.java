package com.place2book.internal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.config.SecurityConfig;
import com.place2book.internal.model.Note;
import com.place2book.internal.service.NoteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoteController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("dev")
class NoteControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    NoteService noteService;

    @Test
    void listNotes() throws Exception {
        when(noteService.findByCaseId(1)).thenReturn(List.of(new Note(3L, 1L, "Hej", "TESTUSER", null)));

        mvc.perform(get("/api/cases/1/notes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(3))
            .andExpect(jsonPath("$[0].caseId").value(1))
            .andExpect(jsonPath("$[0].text").value("Hej"))
            .andExpect(jsonPath("$[0].createdBy").value("TESTUSER"));
    }

    @Test
    void listNotesOfUnknownCaseIsNotFound() throws Exception {
        when(noteService.findByCaseId(42)).thenThrow(new NotFoundException("Case with id 42 not found"));

        mvc.perform(get("/api/cases/42/notes"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.detail").value("Case with id 42 not found"));
    }

    @Test
    void createNoteAsTestUser() throws Exception {
        when(noteService.create(1, "Ringet til arrangøren", "TESTUSER"))
            .thenReturn(new Note(3L, 1L, "Ringet til arrangøren", "TESTUSER", null));

        mvc.perform(post("/api/cases/1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"text": "Ringet til arrangøren"}
                        """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.createdBy").value("TESTUSER"));
    }

    @Test
    void createNoteWithBlankTextIsBadRequest() throws Exception {
        mvc.perform(post("/api/cases/1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"text": "   "}
                        """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.text").exists());

        verify(noteService, never()).create(anyLong(), any(), any());
    }

    @Test
    void createNoteWithoutTextIsBadRequest() throws Exception {
        mvc.perform(post("/api/cases/1/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.text").exists());

        verify(noteService, never()).create(anyLong(), any(), any());
    }

    @Test
    void createNoteOnUnknownCaseIsNotFound() throws Exception {
        when(noteService.create(42, "Hej", "TESTUSER"))
            .thenThrow(new NotFoundException("Case with id 42 not found"));

        mvc.perform(post("/api/cases/42/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"text": "Hej"}
                        """))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteNote() throws Exception {
        mvc.perform(delete("/api/notes/3"))
            .andExpect(status().isNoContent());

        verify(noteService).delete(3);
    }

    @Test
    void deleteUnknownNoteIsNotFound() throws Exception {
        doThrow(new NotFoundException("Note with id 42 not found")).when(noteService).delete(42);

        mvc.perform(delete("/api/notes/42"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.detail").value("Note with id 42 not found"));
    }
}
