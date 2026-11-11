package com.place2book.internal.service;

import java.util.List;

import com.place2book.internal.client.Place2BookClient;
import com.place2book.internal.dto.OrganizerDto;
import com.place2book.internal.dto.OrganizerDto.BankAccountDto;
import com.place2book.internal.dto.OrganizerDto.EventDto;
import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.BankAccount;
import com.place2book.internal.model.Event;
import com.place2book.internal.model.Organizer;

import org.springframework.stereotype.Service;

@Service
public class OrganizerService {

    private final Place2BookClient place2BookClient;

    public OrganizerService(Place2BookClient place2BookClient) {
        this.place2BookClient = place2BookClient;
    }

    public OrganizerDto getOrganizer(Long organizerId) {
        Organizer organizer = place2BookClient.getOrganizer(organizerId)
                .orElseThrow(() -> new NotFoundException("Organizer with id " + organizerId + " not found"));

        List<BankAccountDto> bankAccounts = place2BookClient.getBankAccounts(organizerId)
                .stream()
                .map(this::toBankAccountDto)
                .toList();

        List<EventDto> events = place2BookClient.getEvents(organizerId)
                .stream()
                .map(this::toEventDto)
                .toList();

        return new OrganizerDto(
                organizer.getId(),
                organizer.getName(),
                organizer.getEmail(),
                organizer.getPhone(),
                organizer.getCvr(),
                bankAccounts,
                events);
    }

    private BankAccountDto toBankAccountDto(BankAccount account) {
        return new BankAccountDto(
                account.getId(),
                account.getAccPrefix(),
                account.getAccMain(),
                account.isEventDefault(),
                account.isActive());
    }

    private EventDto toEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getName(),
                event.getBankAccountId());
    }
}