package com.place2book.internal.dataflow;

import java.time.LocalDateTime;

public record ExampleNoteResponse(int id, String text, LocalDateTime CreatedAt) {}
