package com.place2book.internal.controller;

import java.util.List;

import com.place2book.internal.dto.ChangeStatusRequest;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseLog;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.service.CaseService;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cases")
@Tag(name = "Case", description = "Cases for rejected payouts and their status history")
public class CaseController {
    private final CaseService caseService;

    CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    @Operation(summary = "List cases", description = "Returns cases newest first. A null filter will be ignored.")
    @ApiResponse(responseCode = "200", description = "The matching cases")
    @ApiResponse(responseCode = "400", description = "Unknown status",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public List<Case> findAll(
            @Parameter(description = "Only cases with this status")
            @RequestParam(required = false) CaseStatus status,
            @Parameter(description = "Only cases for this organizer")
            @RequestParam(required = false) String organizerId) {
        return caseService.findAll(status, organizerId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a case")
    @ApiResponse(responseCode = "200", description = "The case")
    @ApiResponse(responseCode = "404", description = "Case not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public Case findById(@PathVariable long id) {
        return caseService.findById(id);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change the status of a case", description = "Writes the change to the history of the case.")
    @ApiResponse(responseCode = "200", description = "The updated case")
    @ApiResponse(responseCode = "400", description = "Missing or unknown status",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Case not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public Case changeStatus(@PathVariable long id, @Valid @RequestBody ChangeStatusRequest request) {
        return caseService.changeStatus(id, request.status(), request.performedBy());
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "Get the status history of a case", description = "Returns the entries oldest first.")
    @ApiResponse(responseCode = "200", description = "The history of the case")
    @ApiResponse(responseCode = "404", description = "Case not found",
            content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public List<CaseLog> getHistory(@PathVariable long id) {
        return caseService.getHistory(id);
    }
}
