package com.savan.keycloak.configs;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExtractRolesAndExpiry {
	
	// Extract Access-Token Username
	public String extractAccessTokenUsername(String accessTokenHeader) {
	    String username = null;
	    try {
	        String bearerAccessToken = accessTokenHeader.replace("Bearer ", "");
	        TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
	        AccessToken accessToken = verifier.getToken();
	        username = accessToken.getPreferredUsername();
	    } catch (VerificationException e) {
	        // log.info("An error occurred while extracting the username: {}", e.getMessage());
	    }
	    return username;
	}

	// Extract Access-Token Roles
	public List<String> extractAccessTokenRoles(String accessTokenHeader) {
		List<String> roles = new ArrayList<>();
		try {
			String bearerAccessToken = accessTokenHeader.replace("Bearer ", "");
			TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
			AccessToken accessToken = verifier.getToken();
			roles = new ArrayList<>(accessToken.getRealmAccess().getRoles());
		} catch (VerificationException e) {
			// log.info("An error occurred while extracting the roles: {}", e.getMessage());
		}
		return roles;
	}

	// Check if Access-Token Expired or Valid
	public boolean isAccessTokenExpiredOrValid(String accessTokenHeader) {
        try {
            String bearerAccessToken = accessTokenHeader.replace("Bearer ", "");
            TokenVerifier<AccessToken> verifier = TokenVerifier.create(bearerAccessToken, AccessToken.class);
            AccessToken accessToken = verifier.getToken();

            Long tokenExp = accessToken.getExp();
            if (tokenExp != null) {
                Instant expirationInstant = Instant.ofEpochSecond(tokenExp);
                Instant now = Instant.now();

                /*
                String formattedExpirationTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(expirationInstant);
                log.info("Token Expiration Time: {}", formattedExpirationTime);
                */

                if (now.isAfter(expirationInstant)) {
                    //log.info("access-token is expired.");
                    return false;
                } else {
                    //log.info("access-token is still valid.");
                    return true;
                }
            } else {
                //log.info("Token expiration time is null.");
            }
        } catch (VerificationException e) {
            //log.info("An error occurred while extracting the expiry of token: {}", e.getMessage());
        }
        return false;
    }
}
