package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.math.BigDecimal;


public class AccountService  {

    private final String baseUrl ;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url)  {
        this.baseUrl= url;

    }


    public Balance getBalance(AuthenticatedUser currentUser) {
        Balance balance = new Balance();
        try {
            balance = restTemplate.exchange(baseUrl + "balance", HttpMethod.GET,createAuthEntity(currentUser), Balance.class).getBody();

        } catch (RestClientException m) {
            System.out.println("Error getting balance from the account : "+currentUser.getUser().getUsername());
        }
        return balance;
    }

    public Account getAccountByUserId(AuthenticatedUser currentUser, int userId) {
        Account myAccount = new Account();
        try {
            myAccount = restTemplate.exchange(baseUrl + "account/user/" + userId, HttpMethod.GET, createAuthEntity(currentUser), Account.class).getBody();
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        }
        return myAccount;
    }

    public Account getAccountById(AuthenticatedUser currentUser, int accountId) {
        Account myAccount = new Account();
        try {
            myAccount = restTemplate.exchange(baseUrl +"account/" + accountId, HttpMethod.GET, createAuthEntity(currentUser), Account.class).getBody();
        } catch (RestClientResponseException m) {
            m.printStackTrace();
        }
        return myAccount;
    }


    private HttpEntity createAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}

