package com.place2book.internal.dto;

import java.util.List;


public record OrganizerDto(
        Long id,
        String name,
        String email,
        String phone,
        String cvr,
        List<BankAccountDto> bankAccounts,
        List<EventDto> events) {

    public record BankAccountDto(
            Long id,
            String registrationNumber,
            String accountNumber,
            boolean eventDefault,
            boolean active) {
    }

    public record EventDto(
            Long id,
            String name,
            Long bankAccountId) {
    }
}