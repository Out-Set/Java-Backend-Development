package com.savan.keycloak.keycloakController;

import com.savan.keycloak.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/keycloak")
public class AccessTokenController {

    @Autowired
    private RestTemplateService restTemplateService;

    @PostMapping("/access-token")
    public ResponseEntity<String> getAccessToken(@RequestBody Map<String, String> keycloakAuthRequest) {
        return restTemplateService.getAccessToken(keycloakAuthRequest.get("username"), keycloakAuthRequest.get("password"));
    }
}
