package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
public class TransferStatusService {

    private  String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusService(String url) {
        this.baseUrl = url;
    }


    public TransferStatus getTransferStatus(AuthenticatedUser currentUser, String description) {
        TransferStatus transferStatus = null;
        try {

            transferStatus = restTemplate.exchange(baseUrl + "transferstatus/filter?description=" + description,
                    HttpMethod.GET, makeEntity(currentUser),
                    TransferStatus.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return transferStatus;
    }


    public TransferStatus getTransferStatusById(AuthenticatedUser currentUser, int transferStatusId) {
        TransferStatus transferStatus = null;
        try {
            transferStatus = restTemplate.exchange(baseUrl + "transferstatus/" + transferStatusId, HttpMethod.GET,
                    makeEntity(currentUser),
                    TransferStatus.class).getBody();
        } catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }

        return transferStatus;
    }

    private HttpEntity makeEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
