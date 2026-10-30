package com.place2book.internal.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Retransfer {

    private Long id;
    private Long caseId;
    private BigDecimal amount;
    private String accPrefix; // The new reg.nr.
    private String accMain; // The new account number.
    private LocalDateTime retransferredAt;

    public Retransfer() {
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccPrefix() {
        return accPrefix;
    }

    public void setAccPrefix(String accPrefix) {
        this.accPrefix = accPrefix;
    }

    public String getAccMain() {
        return accMain;
    }

    public void setAccMain(String accMain) {
        this.accMain = accMain;
    }

    public LocalDateTime getRetransferredAt() {
        return retransferredAt;
    }

    public void setRetransferredAt(LocalDateTime retransferredAt) {
        this.retransferredAt = retransferredAt;
    }
}