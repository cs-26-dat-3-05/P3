package com.place2book.internal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction{
    private Long transactionId;
    private Long bankStatementId;
    private String ulid;
    private LocalDate bookingDate;
    private BigDecimal amount;
    private String currency;
    private String remittanceText;
    private LocalDateTime createdAt;

    public Transaction(){}

    // With all fields defined we need setters and getters for all
    public Long getTransactionId(){
        return transactionId;
    }
    public void setTransactionId(Long transactionId){
        this.transactionId = transactionId;
    }

    public Long getBankStatementId(){
        return bankStatementId;
    }
    public void setBankStatementId(Long bankStatementId){
        this.bankStatementId = bankStatementId;
    }

    public String getUlid(){
        return ulid;
    }
    public void setUlid(String ulid){
        this.ulid = ulid;
    }

    public LocalDate getBookingDate(){
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate){
        this.bookingDate = bookingDate;
    }

    public BigDecimal getAmount(){
        return amount;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getCurrency(){
        return currency;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getRemittanceText(){
        return remittanceText;
    }
    public void setRemittanceText(String remittanceText){
        this.remittanceText = remittanceText;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    
}