package com.place2book.internal.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.dto.OrganizerDto;
import com.place2book.internal.dto.OrganizerDto.BankAccountDto;
import com.place2book.internal.service.OrganizerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrganizerController.class)
class OrganizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizerService organizerService;

    @Test
    void getOrganizer_returns200AndJson_whenOrganizerExists() throws Exception {
        OrganizerDto dto = new OrganizerDto(
                42L, "Test Arrangør", "test@example.com", "12345678", "87654321",
                List.of(new BankAccountDto(1L, "1234", "5678901234", true, true)),
                List.of());
        when(organizerService.getOrganizer(42L)).thenReturn(dto);

        mockMvc.perform(get("/api/organizers/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.name").value("Test Arrangør"))
                .andExpect(jsonPath("$.bankAccounts[0].registrationNumber").value("1234"))
                .andExpect(jsonPath("$.events").isEmpty());
    }

    @Test
    void getOrganizer_returns404_whenOrganizerDoesNotExist() throws Exception {
        when(organizerService.getOrganizer(99L))
                .thenThrow(new NotFoundException("Organizer with id 99 not found"));

        mockMvc.perform(get("/api/organizers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Resource not found"))
                .andExpect(jsonPath("$.detail").value("Organizer with id 99 not found"));
    }

    @Test
    void getOrganizer_returns400_whenIdIsNotANumber() throws Exception {
        mockMvc.perform(get("/api/organizers/abc"))
                .andExpect(status().isBadRequest());
    }
}