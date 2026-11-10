package com.place2book.internal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.config.SecurityConfig;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseLog;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.service.CaseService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CaseController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("dev")
class CaseControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CaseService caseService;

    static Case caseWith(long id, CaseStatus status) {
        Case c = new Case();
        c.setId(id);
        c.setStatus(status);
        c.setOrganizerId("4");
        return c;
    }

    @Test
    void listCasesWithoutFilters() throws Exception {
        when(caseService.findAll(null, null)).thenReturn(List.of(caseWith(1, CaseStatus.REGISTERED)));

        mvc.perform(get("/api/cases"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].status").value("REGISTERED"));
    }

    @Test
    void listCasesWithFilters() throws Exception {
        when(caseService.findAll(CaseStatus.ORGANIZER_IDENTIFIED, "4"))
            .thenReturn(List.of(caseWith(2, CaseStatus.ORGANIZER_IDENTIFIED)));

        mvc.perform(get("/api/cases").param("status", "ORGANIZER_IDENTIFIED").param("organizerId", "4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(2))
            .andExpect(jsonPath("$[0].organizerId").value("4"));
    }

    @Test
    void listCasesWithUnknownStatusIsBadRequest() throws Exception {
        mvc.perform(get("/api/cases").param("status", "NOT_A_STATUS"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getCase() throws Exception {
        when(caseService.findById(1)).thenReturn(caseWith(1, CaseStatus.REGISTERED));

        mvc.perform(get("/api/cases/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUnknownCaseIsNotFound() throws Exception {
        when(caseService.findById(42)).thenThrow(new NotFoundException("Case with id 42 not found"));

        mvc.perform(get("/api/cases/42"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.detail").value("Case with id 42 not found"));
    }

    @Test
    void changeStatus() throws Exception {
        when(caseService.changeStatus(1, CaseStatus.BANK_DETAILS_REQUESTED, "Anders"))
            .thenReturn(caseWith(1, CaseStatus.BANK_DETAILS_REQUESTED));

        mvc.perform(patch("/api/cases/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"status": "BANK_DETAILS_REQUESTED", "performedBy": "Anders"}
                        """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("BANK_DETAILS_REQUESTED"));
    }

    @Test
    void changeStatusWithoutStatusIsBadRequest() throws Exception {
        mvc.perform(patch("/api/cases/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"performedBy": "Anders"}
                        """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.status").exists());

        verify(caseService, never()).changeStatus(anyLong(), any(), any());
    }

    @Test
    void changeStatusWithUnknownStatusIsBadRequest() throws Exception {
        mvc.perform(patch("/api/cases/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"status": "NOT_A_STATUS"}
                        """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void changeStatusOfUnknownCaseIsNotFound() throws Exception {
        when(caseService.changeStatus(42, CaseStatus.RETRANSFERRED, null))
            .thenThrow(new NotFoundException("Case with id 42 not found"));

        mvc.perform(patch("/api/cases/42/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"status": "RETRANSFERRED"}
                        """))
            .andExpect(status().isNotFound());
    }

    @Test
    void getHistory() throws Exception {
        CaseLog entry = new CaseLog(5L, 1L, CaseStatus.REGISTERED, "Anders", null);
        when(caseService.getHistory(1)).thenReturn(List.of(entry));

        mvc.perform(get("/api/cases/1/history"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].status").value("REGISTERED"))
            .andExpect(jsonPath("$[0].performedBy").value("Anders"));
    }

    @Test
    void getHistoryOfUnknownCaseIsNotFound() throws Exception {
        when(caseService.getHistory(42)).thenThrow(new NotFoundException("Case with id 42 not found"));

        mvc.perform(get("/api/cases/42/history"))
            .andExpect(status().isNotFound());
    }
}
