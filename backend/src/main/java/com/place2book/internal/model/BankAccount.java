package com.place2book.internal.model;

public class BankAccount {

    private Long id;
    private Long organizerId;
    private String accPrefix; // Reg nr.
    private String accMain; // Konto nr.
    private boolean eventDefault; // Konto for nye events
    private boolean active;

    public BankAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
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

    public boolean isEventDefault() {
        return eventDefault;
    }

    public void setEventDefault(boolean eventDefault) {
        this.eventDefault = eventDefault;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}