package com.place2book.internal.dto.DanskeBankTransactionsAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record PostalAddress(String streetName, String postCode, String townName, String country){}
