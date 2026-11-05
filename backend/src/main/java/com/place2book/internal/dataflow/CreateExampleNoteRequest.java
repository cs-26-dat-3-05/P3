package com.place2book.internal.dataflow;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateExampleNoteRequest(@NotBlank(message = "note must not be blank") @Size(min=1, max=2000) String note) {}
