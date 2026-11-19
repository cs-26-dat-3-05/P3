package com.place2book.internal.service;

import com.place2book.internal.model.Organizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${app.mail.from}")
    private String Place2BookEmail;
    
    private final JavaMailSender mailSender;
    
    EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void requestBbanByMail(Organizer organizer) {
        String mailSubject = "Manglende konto nr";
        
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(Place2BookEmail);
        msg.setTo(organizer.getEmail());
        msg.setSubject(mailSubject);
        msg.setText(
            "Kære" + organizer.getName()
            + ", følg venligst følgende link, for at indtaste nyt konto nr.");
        try {
            mailSender.send(msg);
        }
        catch(MailException e) {
            // TODO: log exception passende sted 
            System.err.println(e.getMessage());
        }
    }
}
