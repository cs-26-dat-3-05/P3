package com.place2book.internal.client;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.model.Organizer;
import com.place2book.internal.model.BankAccount;
import com.place2book.internal.model.Event;
import com.place2book.internal.repository.P2BRepository;

import org.springframework.stereotype.Component;

@Component
public class Place2BookClient {

    private final P2BRepository p2bRepository;

    public Place2BookClient(P2BRepository p2bRepository) {
        this.p2bRepository = p2bRepository;
    }

    public Optional<Organizer> getOrganizer(Long organizerId) {
        return p2bRepository.findOrganizerById(organizerId);
    }

    public List<BankAccount> getBankAccounts(Long organizerId) {
        return p2bRepository.findBankAccountsByOrganizerId(organizerId);
    }

    public List<Event> getEvents(Long organizerId) {
        return p2bRepository.findEventsByOrganizerId(organizerId);
    }
}