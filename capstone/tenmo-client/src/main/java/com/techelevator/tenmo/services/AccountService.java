package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private User user;

    public AccountService(String url, User user) {
        this.baseUrl = url;
        this.user = user;
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





}
