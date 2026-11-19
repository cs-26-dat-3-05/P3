package com.place2book.internal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateNoteRequest(
        @Schema(description = "The content of the note", example = "Ringet til arrangøren og venter på svar.")
        @NotBlank
        @Size(max = 5000)
        String text) {
}
