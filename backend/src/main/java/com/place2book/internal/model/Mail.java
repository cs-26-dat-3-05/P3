package com.place2book.internal.model;

import java.time.LocalDateTime;

public class Mail {
    private Long id;
    private Long caseId; // which case the mail belongs to
    private String subject;
    private String body;
    private String recipient; // mail recipient
    private LocalDateTime sentAt;
    private status mailStatus;
    public enum status {
        FAIL,
        SUCCESS
    }    
    public Mail() {

    }

    public status getMailStatus() {
        return mailStatus;
    }
    
    public void setMailStatus(status status) {
        this.mailStatus = status;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
