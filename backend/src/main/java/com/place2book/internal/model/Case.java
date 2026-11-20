package com.place2book.internal.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Case {

    private Long id;
    private CaseStatus status;
    private String organizerId;
    private String organizerName;
    private Long transactionId; // the rejected payout from the bank statement
    private String eventId;
    private String rejectedRegistrationNumber; // Reg nr. the payout was rejected from
    private String rejectedAccountNumber; // Konto nr. the payout was rejected from
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private boolean requiresAction;

    public Case(Long id, CaseStatus status, String organizerId, String organizerName, Long transactionId,
            String eventId,
            String rejectedRegistrationNumber, String rejectedAccountNumber, BigDecimal amount,
            LocalDateTime createdAt, boolean requiresAction) {
        this.id = id;
        this.status = status;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.transactionId = transactionId;
        this.eventId = eventId;
        this.rejectedRegistrationNumber = rejectedRegistrationNumber;
        this.rejectedAccountNumber = rejectedAccountNumber;
        this.amount = amount;
        this.createdAt = createdAt;
        this.requiresAction = requiresAction;

    }

    public Case() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRejectedRegistrationNumber() {
        return rejectedRegistrationNumber;
    }

    public void setRejectedRegistrationNumber(String rejectedRegistrationNumber) {
        this.rejectedRegistrationNumber = rejectedRegistrationNumber;
    }

    public String getRejectedAccountNumber() {
        return rejectedAccountNumber;
    }

    public void setRejectedAccountNumber(String rejectedAccountNumber) {
        this.rejectedAccountNumber = rejectedAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getRequiresAction() {
        return requiresAction;
    }

    public void setRequiresAction(boolean requiresAction) {
        this.requiresAction = requiresAction;
    }
}
