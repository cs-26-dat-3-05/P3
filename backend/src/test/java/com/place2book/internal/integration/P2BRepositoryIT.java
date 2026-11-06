package com.place2book.internal.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.place2book.internal.model.Organizer;
import com.place2book.internal.repository.P2BRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class P2BRepositoryIT {

    private final P2BRepository p2brepo;
    
    @Autowired
    P2BRepositoryIT(P2BRepository p2brepo) {
        this.p2brepo = p2brepo;
    }
    
    @Test
    void findOrganizerById() {
        Organizer organizer = p2brepo.findOrganizerById(1).orElseThrow();
        assertThat(organizer.getName()).isEqualTo("Aalborg Jazz Festival");
    }
    
    @Test
    void returnEmptyOptionalWhenIdDoesNotExist() {
        Organizer organizer = p2brepo.findOrganizerById(-1).orElse(new Organizer());
        assertThat(organizer.getName()).isEqualTo(null);
    }
}
