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

public class TransferService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TransferService(String url) {
        this.baseUrl = url;
    }

    public void printListOfTransferByAccountId(long accountId){
        Transfer[] transfers = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfer/" + accountId + "/transferslist",
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();

            for (Transfer transfer : transfers){
                if (transfer.getAccountTo() == accountId) {
                    System.out.println(transfer.getTransferId() + "      " +  "From: " + transfer.getFromUser() + "          $" + transfer.getAmount());
                } else {
                    System.out.println(transfer.getTransferId() + "      " + "To: " + transfer.getToUser()  + "              $" + transfer.getAmount());
                }
            }
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Transfer printTransferByTransferId(long transferId) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.getForObject(baseUrl + "transfer/" + transferId, Transfer.class);
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public Transfer addTransfer(Transfer transfer){
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        Transfer returnedTransfer = null;
        try{
            returnedTransfer = restTemplate.postForObject(baseUrl + "transfer/", entity, Transfer.class);
        }catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }

    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
