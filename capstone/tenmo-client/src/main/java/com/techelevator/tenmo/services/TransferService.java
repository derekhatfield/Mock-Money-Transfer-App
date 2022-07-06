package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private User user;
    private Account account;
    private String authToken = null;
    private AuthenticatedUser currentUser;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TransferService(String url, User user, Account account, AuthenticatedUser currentUser) {
        this.baseUrl = url;
        this.user = user;
        this.account = account;
        this.currentUser = currentUser;
    }


    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public Transfer add(Transfer transfer){
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        Transfer returnedTransfer = null;

        try{
            returnedTransfer = restTemplate.postForObject(baseUrl + "transfer/", entity, Transfer.class);
        }catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }










}
