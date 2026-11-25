package com.place2book.internal.dto.DanskeBankTransactionsAPI;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionResponse(List<Transaction> transactions) {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Transaction(String transactionId, AmountDetails amountDetails, Creditor creditor, CreditorAccount creditorAccount){
        
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AmountDetails(InstructedAmount instructedAmount){}

        public record Creditor(String name, PostalAddress postalAddress){}
        
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CreditorAccount(String iban, String bban){}
    }
}
