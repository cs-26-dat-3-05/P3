package com.place2book.internal.controller;

import com.place2book.internal.dto.OrganizerDto;
import com.place2book.internal.service.OrganizerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizers")
@Tag(name = "Organizers", description = "Look up organizers from Place2Book")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    /**
     * Returns an organizer with their bank accounts and events.
     * <p>
     *
     * @param id the organizer's id in Place2Book
     * @return the organizer as an {@link OrganizerDto}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get organizer by id", description = "Returns the organizer's contact information, bank accounts and events from Place2Book.")
    @ApiResponse(responseCode = "200", description = "Organizer found")
    @ApiResponse(responseCode = "400", description = "The id is not a valid number", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "No organizer with the given id", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public OrganizerDto getOrganizer(
            @Parameter(description = "The organizer's id in Place2Book", example = "55") @PathVariable Long id) {
        return organizerService.getOrganizer(id);
    }
}