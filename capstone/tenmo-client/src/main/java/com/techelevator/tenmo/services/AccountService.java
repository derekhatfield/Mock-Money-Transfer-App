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
    private AuthenticatedUser currentUser;

    public AccountService(String url, User user, AuthenticatedUser currentUser) {
        this.baseUrl = url;
        this.user = user;
        this.currentUser = currentUser;
    }

    public void setAuthToken(String authToken) {
        this.authToken = currentUser.getToken();
    }

    public Account getAccount(long accountId) {
        Account account = null;
        try {
            account = restTemplate.getForObject(baseUrl + "account/" + accountId, Account.class);
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public BigDecimal getBalanceByAccountId(long accountId) {
        BigDecimal balance = null;
        try {
            balance = restTemplate.getForObject(baseUrl + "account/" + accountId, BigDecimal.class);
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public void printListOfUsers() {

        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "listusers", HttpMethod.GET, makeAuthEntity(), User[].class);
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
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }



}
