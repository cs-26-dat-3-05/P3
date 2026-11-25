package com.place2book.internal;

import java.beans.BeanProperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import com.place2book.internal.client.DanskeBankClient;
import com.place2book.internal.dto.DanskeBankTransactionsAPI.TransactionResponse;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        ApplicationContext SpringContainer = SpringApplication.run(Main.class, args);


        try{
            DanskeBankClient client = SpringContainer.getBean(DanskeBankClient.class);
            TransactionResponse response = client.ReturnedTransactions();
            System.out.println(response);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
