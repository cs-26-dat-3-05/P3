package com.place2book.internal.model;

public enum CaseStatus {
    REGISTERED, // 1. Returnering registreret
    ORGANIZER_IDENTIFIED, // 2. Arrangør identificeret
    BANK_DETAILS_REQUESTED, // 3. Nye bankoplysninger efterspurgt
    AWAITING_ORGANIZER, // 4. Afventer korrekt kontonummer
    BANK_DETAILS_RECEIVED, // 5. Nye oplysninger indtastet
    RETRANSFERRED // 6. Udbetaling genindsendt
}