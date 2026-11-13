package com.place2book.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.client.Place2BookClient;
import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.dto.OrganizerDto;
import com.place2book.internal.model.BankAccount;
import com.place2book.internal.model.Event;
import com.place2book.internal.model.Organizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrganizerServiceTest {

    @Mock
    private Place2BookClient place2BookClient;

    @InjectMocks
    private OrganizerService organizerService;

    @Test
    void getOrganizer_returnsDtoWithAccountsAndEvents_whenOrganizerExists() {
        Organizer organizer = new Organizer();
        organizer.setId(42L);
        organizer.setName("Test Arrangør");
        organizer.setEmail("test@example.com");
        organizer.setPhone("12345678");
        organizer.setCvr("87654321");

        BankAccount account = new BankAccount(1L, 1L, "1234", "5678901234", true, true);

        Event event = new Event(7L, 1L, 1L, "Koncert", 420.69);

        when(place2BookClient.getOrganizer(42L)).thenReturn(Optional.of(organizer));
        when(place2BookClient.getBankAccounts(42L)).thenReturn(List.of(account));
        when(place2BookClient.getEvents(42L)).thenReturn(List.of(event));

        OrganizerDto result = organizerService.getOrganizer(42L);

        assertEquals(42L, result.id());
        assertEquals("Test Arrangør", result.name());
        assertEquals("87654321", result.cvr());

        assertEquals(1, result.bankAccounts().size());
        assertEquals("1234", result.bankAccounts().get(0).registrationNumber());
        assertEquals("5678901234", result.bankAccounts().get(0).accountNumber());

        assertEquals(1, result.events().size());
        assertEquals("Koncert", result.events().get(0).name());
        assertEquals(1L, result.events().get(0).bankAccountId());
    }

    @Test
    void getOrganizer_returnsEmptyLists_whenOrganizerHasNoAccountsOrEvents() {
        Organizer organizer = new Organizer();
        organizer.setId(42L);

        when(place2BookClient.getOrganizer(42L)).thenReturn(Optional.of(organizer));
        when(place2BookClient.getBankAccounts(42L)).thenReturn(List.of());
        when(place2BookClient.getEvents(42L)).thenReturn(List.of());

        OrganizerDto result = organizerService.getOrganizer(42L);

        assertTrue(result.bankAccounts().isEmpty());
        assertTrue(result.events().isEmpty());
    }

    @Test
    void getOrganizer_throwsNotFound_whenOrganizerDoesNotExist() {
        when(place2BookClient.getOrganizer(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> organizerService.getOrganizer(99L));

        // Servicen må ikke hente konti og events, når arrangøren ikke findes
        verify(place2BookClient, never()).getBankAccounts(any());
        verify(place2BookClient, never()).getEvents(any());
    }
}
