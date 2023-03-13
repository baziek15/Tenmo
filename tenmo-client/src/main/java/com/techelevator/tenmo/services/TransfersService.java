package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransfersService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    public TransfersService(String url) {
        this.baseUrl = url;
    }


    public void createTransfer(AuthenticatedUser currentUser, Transfer transfer) {

        String url = baseUrl + "transfers/" + transfer.getTransferId();

        try {
            restTemplate.exchange(url, HttpMethod.POST,makeTransferEntity(currentUser, transfer), Transfer.class);
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }

    }


    public Transfer[] getTransfersFromUserId(AuthenticatedUser currentUser, int userId) {
        Transfer[] transfers =  null;;
        try {
            transfers = restTemplate.exchange(baseUrl + "transfers/user/" + userId,
                    HttpMethod.GET,
                    makeAuthEntity(currentUser),
                    Transfer[].class
            ).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return transfers;
    }


    public Transfer getTransferFromTransferId(AuthenticatedUser currentUser, int id) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.exchange(baseUrl + "transfers/" + id,
                    HttpMethod.GET,
                    makeAuthEntity(currentUser),
                    Transfer.class
            ).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return transfer;
    }


    public Transfer[] getAllTransfers(AuthenticatedUser currentUser) {
        Transfer[] transfers = null;

        try {
            transfers = restTemplate.exchange(baseUrl + "transfers",
                    HttpMethod.GET,
                    makeAuthEntity(currentUser),
                    Transfer[].class
            ).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return transfers;
    }

    public Transfer[] getPendingTransfersByUserId(AuthenticatedUser currentUser) {

        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "transfers/user/"+ currentUser.getUser().getId() + "/pending",
                    HttpMethod.GET,
                    makeAuthEntity(currentUser),
                    Transfer[].class
            ).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return transfers;
    }

    public void updateTransfer(AuthenticatedUser currentUser, Transfer transfer) {

        try {
            restTemplate.exchange(baseUrl + "transfers/" + transfer.getTransferId(),
                    HttpMethod.PUT,
                    makeTransferEntity(currentUser, transfer),
                    Transfer.class);
        }
        catch(RestClientResponseException e) {
            System.out.println("Error While updating the Transfer Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
    }

    private HttpEntity<Transfer> makeTransferEntity(AuthenticatedUser currentUser,Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity makeAuthEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity(headers);
    }
}