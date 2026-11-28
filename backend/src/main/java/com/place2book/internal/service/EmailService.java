package com.place2book.internal.service;

import com.place2book.internal.client.Place2BookClient;
import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.Mail;
import com.place2book.internal.model.Mail.status;
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
    private final Place2BookClient p2bclient;
    
    EmailService(JavaMailSender mailSender, Place2BookClient p2bclient) {
        this.mailSender = mailSender;
        this.p2bclient = p2bclient;
    }
    
    private Mail createBbanRequestMail(Case _case) {
        Mail mail = new Mail();
        // TODO: fix type mismatch mellem organizerId i Case og p2bclient
        // måske refactor så db ikke kaldes
        Organizer o = p2bclient.getOrganizer(Long.valueOf(_case.getOrganizerId())).orElseThrow(() -> new NotFoundException("Organizer with id " + _case.getOrganizerId() + " not found"));
        mail.setCaseId(_case.getId());
        mail.setRecipient(o.getEmail());
        mail.setSubject("Manglende konto nummer");
        mail.setBody(
            "Hej " + o.getName() + "\n\n"
            + "Vi har forsøgt at overføre " + _case.getAmount() + " DKK "
            + "til din bankkonto (" + _case.getRejectedRegistrationNumber()
            + "-" + _case.getRejectedAccountNumber() + "), men overførslen "
            + "er desværre blevet afvist af din bank.\n\n"
            + "For at vi kan gennemføre overførslen, bedes du venligst "
            + "opdatere dine kontooplysninger under din profil på vores hjemmeside.\n\n"
            + "Når dine oplysninger er blevet opdateret, kan vi forsøge "
            + "at gennemføre overførslen igen.\n\n"
            + "Med venlig hilsen\n"
            + "Kundeservice"
            );
        return mail;
    }
    
    public void requestBbanByMail(Case _case) {
        Mail mail = createBbanRequestMail(_case);
        
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(Place2BookEmail);
        msg.setTo(mail.getRecipient());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getBody());
        try {
            mailSender.send(msg);
            mail.setMailStatus(status.SUCCESS);
        }
        catch(MailException e) {
            // TODO: log exception passende sted 
            System.err.println(e.getMessage());
            mail.setMailStatus(status.FAIL);
        }
    }
}
