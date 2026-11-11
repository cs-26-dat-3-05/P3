package com.place2book.internal.dto;

import com.place2book.internal.model.CaseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Body for changing the status of a case.
 */
public record ChangeStatusRequest(
        @Schema(description = "The new status of the case", example = "ORGANIZER_IDENTIFIED")
        @NotNull
        CaseStatus status,

        @Schema(description = "Who made the change. Optional until login is added", example = "Anders", nullable = true)
        @Size(max = 100)
        String performedBy) {
}
