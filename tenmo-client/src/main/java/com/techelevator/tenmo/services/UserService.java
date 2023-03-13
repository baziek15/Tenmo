package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
public class UserService{

    private RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080/";

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public User[] getAllUsers(AuthenticatedUser currentUser) {
        User[] users = null;
        try {
            users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeEntity(currentUser), User[].class).getBody();
        }  catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return users;
    }


    public User getUserByUserId(AuthenticatedUser currentUser, int id) {
        User user = null;
        try {
            user = restTemplate.exchange(BASE_URL + "/users/" + id, HttpMethod.GET, makeEntity(currentUser), User.class).getBody();
        }  catch(RestClientResponseException e) {
            System.out.println("Error Code: " + e.getRawStatusCode());
        } catch(ResourceAccessException e) {
            System.out.println("Server network issue");
        }
        return user;
    }

    private HttpEntity makeEntity(AuthenticatedUser currentUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        return new HttpEntity(httpHeaders);
    }


}
