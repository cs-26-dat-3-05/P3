package com.place2book.internal.model;

import java.time.LocalDateTime;

public class Case {

    private Long id;
    private CaseStatus status;
    private Long organizerId;
    private Long rejectedPayoutId;
    private LocalDateTime createdAt;

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

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Long getRejectedPayoutId() {
        return rejectedPayoutId;
    }

    public void setRejectedPayoutId(Long rejectedPayoutId) {
        this.rejectedPayoutId = rejectedPayoutId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}