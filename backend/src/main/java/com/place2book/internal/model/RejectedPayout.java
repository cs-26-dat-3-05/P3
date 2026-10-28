package com.place2book.internal.model;

import java.time.LocalDateTime;

public class RejectedPayout {

    private Long id;
    private Long bankStatementId; // amount and account are found here
    private LocalDateTime registeredAt; // when it was registered as rejected

    public RejectedPayout() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankStatementId() {
        return bankStatementId;
    }

    public void setBankStatementId(Long bankStatementId) {
        this.bankStatementId = bankStatementId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}