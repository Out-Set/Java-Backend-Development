package com.savan.keycloak.keycloakController;

import com.savan.keycloak.service.CommonHelper;
import com.savan.keycloak.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
keycloak.admin.client.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/clients
*/

@RestController
@RequestMapping("/keycloak/clients")
public class ClientController {

    @Value("${keycloak.admin.client.base.url}")
    private String clientBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Get clients in a realm
    @GetMapping("")
    public ResponseEntity<String> getClients(@RequestParam(required = false) Map<String, Object> queryParams) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl, HttpMethod.GET, null, accessToken, queryParams);
    }

    // Get client-by-id
    @GetMapping("/{clientId}")
    public ResponseEntity<String> getClientById(@PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl+"/"+clientId, HttpMethod.GET, null, accessToken, null);
    }

    // Create new-client
    @PostMapping("")
    public ResponseEntity<String> createNewClient(@RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl, HttpMethod.POST, requestBody, accessToken, null);
    }

    // Update client-by-id
    @PutMapping("/{clientId}")
    public ResponseEntity<String> updateClientById(@PathVariable String clientId, @RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl+"/"+clientId, HttpMethod.PUT, requestBody, accessToken, null);
    }

    // Get the client secret
    @GetMapping("/{clientId}/client-secret")
    public ResponseEntity<String> getClientSecret(@PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl+"/"+clientId+"/client-secret", HttpMethod.GET, null, accessToken, null);
    }

    // Generate a new secret for the client
    @PostMapping("/{clientId}/client-secret")
    public ResponseEntity<String> generateClientSecret(@PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl+"/"+clientId+"/client-secret", HttpMethod.POST, null, accessToken, null);
    }

    // Delete client-by-id
    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClientById(@PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientBaseUrl+"/"+clientId, HttpMethod.DELETE, null, accessToken, null);
    }
}
