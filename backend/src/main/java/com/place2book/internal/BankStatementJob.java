package com.place2book.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.place2book.internal.client.DanskeBankClient;
import com.place2book.internal.dto.DanskeBankTransactionsAPI.TransactionResponse;

@Component 
public class BankStatementJob {
    Logger logger = LoggerFactory.getLogger(BankStatementJob.class);

    private final DanskeBankClient apiInitiator;
    
    public BankStatementJob(DanskeBankClient apiInitiator){
        this.apiInitiator = apiInitiator;
    }
    
    /**
    * Schedueles the API call that fetches transactions that has been returned 
    *
    * The schedueled API call can be configured by the fixedDelay property. 
    * fixedDelay makes sure that there is for now 15 minutes (900000 miliseconds), 
    * between each API call.
    * 
    * TO DO!!!!!! REMEMBER TO GRAB THE TRANSACTIONS WITH BankStatementService !!
    **/
    @Scheduled(fixedDelay =  900000)
    public void ScheduledBankStatement(){
        try{
            TransactionResponse response = this.apiInitiator.ReturnedTransactions();
        }
        catch(RestClientException e){
            logger.error("API Error: " + e);
        }
        catch(Exception e){
            logger.error("UnexpectedError " + e);
        }
    }




}
