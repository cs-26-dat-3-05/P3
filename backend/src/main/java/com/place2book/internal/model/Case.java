package com.place2book.internal.model;

public class Case {

    private Long id;
    private CaseStatus status;
    private Long organizerId;
    private Long rejectedPayoutId;

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
}