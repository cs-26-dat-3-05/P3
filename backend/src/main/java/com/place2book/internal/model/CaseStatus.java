package com.place2book.internal.model;

public enum CaseStatus {
    REGISTERED, // 1. Returnering registreret
    ORGANIZER_IDENTIFIED, // 2. Arrangør identificeret
    BANK_DETAILS_REQUESTED, // 3. Nye bankoplysninger efterspurgt
    BANK_DETAILS_RECEIVED, // 4. Nye oplysninger indtastet
    RETRANSFERRED // 5. Udbetaling genindsendt
}
