package com.nedra.gateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@Service
public class KeycloakUserService {

   /* @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private String cachedAccessToken;
    private long tokenExpirationTime;

    // Fetches the list of users from Keycloak without using DTO
    public List<Map<String, Object>> getUsers() {
        String accessToken = getAdminAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = keycloakServerUrl + "/admin/realms/" + realm + "/users";

        // Fetch users as a List of Maps
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        return response.getBody();  // Returns the list of users as a Map
    }

    // Gets the admin access token to call the Keycloak Admin API
    private String getAdminAccessToken() {
        // Check if the cached token is still valid
        if (cachedAccessToken != null && System.currentTimeMillis() < tokenExpirationTime) {
            return cachedAccessToken;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        String url = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                cachedAccessToken = responseBody.get("access_token").toString();
                int expiresIn = (Integer) responseBody.get("expires_in");
                tokenExpirationTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn);
                return cachedAccessToken;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get admin access token from Keycloak", e);
        }

        throw new RuntimeException("Access token is null");
    }*/
}