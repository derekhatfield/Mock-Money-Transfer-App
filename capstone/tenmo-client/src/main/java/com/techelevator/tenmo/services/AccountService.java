package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private User user;
    private String authToken = null;


    public AccountService(String url) {
        this.baseUrl = url;
        //this.user = user;

    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Account getAccount(long userId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + "account/" + userId, Account.class);
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }


    public BigDecimal getBalanceByAccountId(long accountId) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.getForObject(baseUrl + "account/balance/" + accountId, BigDecimal.class);
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public void printListOfUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "account/userslist", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();

            for (User user : users) {
                System.out.println(user.getId() + "      " + user.getUsername());
            }
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

    }








    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }



}
