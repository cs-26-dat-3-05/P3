package com.place2book.internal.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankStatement {

    private Long id;
    private BigDecimal amount;
    private String accMain; // Konto nr.
    private String accPrefix; // Reg nr.
    private LocalDateTime importedAt;

    public BankStatement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccMain() {
        return accMain;
    }

    public void setAccMain(String accMain) {
        this.accMain = accMain;
    }

    public String getAccPrefix() {
        return accPrefix;
    }

    public void setAccPrefix(String accPrefix) {
        this.accPrefix = accPrefix;
    }

    public LocalDateTime getImportedAt() {
        return importedAt;
    }

    public void setImportedAt(LocalDateTime importedAt) {
        this.importedAt = importedAt;
    }
}