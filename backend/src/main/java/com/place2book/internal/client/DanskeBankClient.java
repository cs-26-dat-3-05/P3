package com.place2book.internal.client;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.place2book.internal.dto.DanskeBankTransactionsAPI.TransactionResponse;;

@Component 
public class DanskeBankClient {
    private final RestClient restClient;

    public DanskeBankClient(RestClient.Builder RestClientBuilder){
        /*  timeouts confugired in application.properties 
            connect-timeout: 5s
            read-timeout: 60s
        */
        this.restClient = RestClientBuilder.baseUrl("https://mock-api.danskebank.com/mock/sandbox/1.6.1/account-transaction-balance-api/v1/account-transaction-service/accounts/e03ef874-b6c5-5dbf-ac63-eae96984de22/transactions?bookingDateFrom=2025-10-10&bankTransactionCode=PMNT-RCDT-DMCT").build();
    }

    // specifically fetch the account's transaction with returned payments with the bankTransactionCode of: PMNT-RCDT-RRTN //
    public TransactionResponse ReturnedTransactions() throws Exception{
        return this.restClient.get().retrieve().body(TransactionResponse.class);
    }
}
