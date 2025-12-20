package com.savan.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtValidator {


    // private static final String KEYCLOAK_CERTS_URL = "http://localhost:8182/realms/myrealm/protocol/openid-connect/certs"; // Replace with your Keycloak URL

    public static Map<String, Object> extractAccessTokenDetails(String accessTokenHeader) {
        Map<String, Object> tokenDetails = new HashMap<>();
        try {
            String bearerAccessToken = accessTokenHeader.replace("Bearer ", "");

            // Verifying the token and getting AccessToken object
            TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
            AccessToken accessToken = verifier.getToken();
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(accessToken));

            // Extracting various claims from the token
            tokenDetails.put("username", accessToken.getPreferredUsername());
            tokenDetails.put("subject", accessToken.getSubject());
            tokenDetails.put("email", accessToken.getEmail());
            tokenDetails.put("givenName", accessToken.getGivenName());
            tokenDetails.put("familyName", accessToken.getFamilyName());
            tokenDetails.put("expiration", accessToken.getExpiration());
            tokenDetails.put("issuedAt", accessToken.getIssuedAt());
            tokenDetails.put("audience", accessToken.getAudience());
            tokenDetails.put("realmAccess", accessToken.getRealmAccess());
            tokenDetails.put("resourceAccess", accessToken.getResourceAccess());
        } catch (VerificationException | JsonProcessingException e) {
            // log.info("An error occurred while extracting token details: {}", e.getMessage());
            System.out.println("An error occurred while extracting token details: "+ e.getMessage());
        }
        return tokenDetails;
    }


    public static void main(String[] args) throws IOException {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJFX1F4VUZfQlNHaVIxS2EwbUtQTHRteFZzcnloWTJIWjB5NU9qamotZ3VBIn0.eyJleHAiOjE3NDI0ODk3NDIsImlhdCI6MTc0MjQ3MTc0MiwianRpIjoiMTg3ZGU3NTctMmQ4Zi00MzQzLWI5MGQtZTVkMDZmMmFhODA4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9CaXRzRmxvdyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJiNDQzN2Q0ZS0xOTg0LTRjMDQtODY0Yy1hNjUyMTM0MTc0YjgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJERU1PIiwic2lkIjoiYWRkMGQ3ZTYtYjQzNi00ZmYyLTg2YTctNDM3NjZlMmU2MTE1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJIUk1TX1BBWVJPTExfU1lOQ19BUEkiLCJBUElGTE9XIiwib2ZmbGluZV9hY2Nlc3MiLCJDWUdORVRfSVRSX0NBTExCQUNLX0FQSSIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1iaXRzZmxvdyJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiYXNkZiBhc2RmIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYXNkZiIsImdpdmVuX25hbWUiOiJhc2RmIiwiZmFtaWx5X25hbWUiOiJhc2RmIiwiZW1haWwiOiJhc2RmQGdtYWlsLmNvbSJ9.Qe4ZsbSo_nesm954HP_Be5VaU02yhMijrxMVoGWQozEqjZ4C1wCl8wbe9QeISyYpmrh2u2isyckRMqjroxlt4zRCqmhlBHfLaTtk4G3MXpBtwpIzppsAZ3m1xYiNb2y_hzkclNklvNc2aRhKR-mCvHHYeaxz6ezBngZmLZplZ8uWFoJvO80REVaAjwoHAAoVgbYIzp7vACCTxBJFYKbX-H0qmR1oVe-aN415e_aE3A32gL0_ZYq9p4wGzwD-bdA3SU7KhGceZN2paUpQJ_QvmjnCk90uUSibqN9nHaOkW2fzyBXDhbvvvuuix64_yFlWZTYzD2W3iFh6VUub6zOYeA";
        Map<String, Object> dtl = extractAccessTokenDetails(jwtToken);
        System.out.println("JWT Token is valid: " + dtl);
    }
}
