package com.place2book.internal.controller;

import com.place2book.internal.model.Organizer;
import com.place2book.internal.repository.P2BRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    private final P2BRepository p2brepo;
    
    SomeController(P2BRepository p2brepo) {
        this.p2brepo = p2brepo;
    }
    
    @GetMapping("/api/organizer")
    Organizer getOrganizer(int id) {
        return p2brepo.findOrganizerById(id);
    }
}
