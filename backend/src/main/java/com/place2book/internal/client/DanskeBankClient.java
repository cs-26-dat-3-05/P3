package com.place2book.internal.client;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.place2book.internal.dto.DanskeBankTransactionsAPI.TransactionResponse;;

@Component 
public class DanskeBankClient {
    private final RestClient restClient;
   /*  
    *   The dependency "spring-boot-starter-restclient" enables autoconfiguration of a RestClient.Builder bean.
    *   When the class is instantiated, the bean is injected through dependency injection
    *   The function configures and builds the Restclient to initate API calls
    *   
    *   timeouts confugired in application.yml
    *   connect-timeout: 5s
    *   read-timeout: 60s
    * 
    * 
    */
    public DanskeBankClient(RestClient.Builder RestClientBuilder){
        this.restClient = RestClientBuilder.baseUrl("https://mock-api.danskebank.com/mock/sandbox/1.6.1/account-transaction-balance-api/v1/account-transaction-service/accounts/e03ef874-b6c5-5dbf-ac63-eae96984de22/transactions?bookingDateFrom=2025-10-10&bankTransactionCode=PMNT-RCDT-DMCT").build();
    }

    /** 
     * Method that initiates the API call to Danske bank and retrieves the transactions.
     * Specifically fetch the account's transaction with returned payments with the bankTransactionCode of: PMNT-RCDT-RRTN
     * 
     * @return returns the record TransactionResponse, look at the record to see the chosen extracted fields the transaction.
     * @throws RestClientException if the API request fails
     **/
    public TransactionResponse ReturnedTransactions() {
        return this.restClient.get().retrieve().body(TransactionResponse.class);
    }
}
