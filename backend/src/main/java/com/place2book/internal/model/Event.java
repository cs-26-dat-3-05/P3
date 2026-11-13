package com.place2book.internal.model;

public class Event {

    private Long id;
    private Long bankAccountId; // Kontoen eventet udbetaler til
    private Long organizerId;
    private String name;
    private double amount;

    public Event(Long id, Long bankAccountId, Long organizerId, String name, double amount) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.organizerId = organizerId;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }
    
    public double getEventAmount(double amount) {
        return amount;
    }
    
    public void setEventAmount(double amount) {
        this.amount = amount;
    }
}
