package com.place2book.internal.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.place2book.internal.client.Place2BookClient;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.model.Organizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock JavaMailSender mailSender;
    @Mock Place2BookClient p2bclient;
    
    @InjectMocks
    EmailService mailService;
    
    @Test
    void requestBbanByMail_sendsCorrectEmail() {
        Case c = new Case();
        c.setOrganizerId("1");
        c.setRejectedRegistrationNumber("1234");
        c.setRejectedAccountNumber("56789012");
        c.setAmount(BigDecimal.valueOf(67));
        
        Organizer o = new Organizer();
        o.setName("Anton");
        o.setEmail("anton@aau.dk");
        Optional<Organizer> oo = Optional.of(o);
        
        when(p2bclient.getOrganizer(1L)).thenReturn(oo);
        
        mailService.requestBbanByMail(c);
        
        verify(mailSender).send(any(SimpleMailMessage.class));
        verify(mailSender).send(argThat((SimpleMailMessage mail) -> mail.getSubject().equals("Manglende konto nummer")));
        verify(mailSender).send(argThat((SimpleMailMessage mail) -> {
                    mail.getSubject().equals("Manglende konto nummer");
                }));
    }
}
