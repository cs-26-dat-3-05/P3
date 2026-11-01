package com.place2book.internal.dataflow;

import java.time.LocalDateTime; 

public record Note(int id, String text, LocalDateTime CreatedAt) {}
