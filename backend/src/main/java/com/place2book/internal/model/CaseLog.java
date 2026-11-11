package com.place2book.internal.model;

import java.time.LocalDateTime;

public class CaseLog {

    private Long id;
    private Long caseId;
    private CaseStatus status;
    private String performedBy; // null if unknown
    private LocalDateTime createdAt;

    public CaseLog(Long id, Long caseId, CaseStatus status, String performedBy, LocalDateTime createdAt) {
        this.id = id;
        this.caseId = caseId;
        this.status = status;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
    }

    public CaseLog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
