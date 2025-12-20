package com.savan.keycloak.keycloakController;

import com.savan.keycloak.service.CommonHelper;
import com.savan.keycloak.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
keycloak.admin.client.roles.mapping.with.user.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/users/{{user-id}}/role-mappings/clients/{{client-uuid}}
*/

@RestController
@RequestMapping("/keycloak/users/{userId}/role-mappings/clients/{clientId}")
public class ClientRoleMappingWithUserController {

    @Value("${keycloak.admin.client.roles.mapping.with.user.base.url}")
    private String clientUsersRoleMappingBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Get available client-level roles
    @GetMapping("/available")
    public ResponseEntity<String> getAvailableRoles(@PathVariable String userId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientUsersRoleMappingBaseUrl+"/"+userId+"/role-mappings/clients/"+clientId+"/available", HttpMethod.GET, null, accessToken, null);
    }

    // Get effective client-level role mappings
    @GetMapping("/composite")
    public ResponseEntity<String> getEffectiveRoles(@PathVariable String userId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientUsersRoleMappingBaseUrl+"/"+userId+"/role-mappings/clients/"+clientId+"/composite", HttpMethod.GET, null, accessToken, null);
    }

    // Delete client-level roles from user or group role mapping
    @DeleteMapping("")
    public ResponseEntity<String> deleteEffectiveRoles(@PathVariable String userId, @PathVariable String clientId, @RequestBody List<Map<String, Object>> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientUsersRoleMappingBaseUrl+"/"+userId+"/role-mappings/clients/"+clientId, HttpMethod.DELETE, requestBody, accessToken, null);
    }

    // Get client-level role mappings
    @GetMapping("")
    public ResponseEntity<String> getClientLevelRoles(@PathVariable String userId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientUsersRoleMappingBaseUrl+"/"+userId+"/role-mappings/clients/"+clientId, HttpMethod.GET, null, accessToken, null);
    }

    // Add client-level roles to the user
    @PostMapping("")
    public ResponseEntity<String> getClientLevelRolesToUser(@PathVariable String userId, @PathVariable String clientId, @RequestBody List<Map<String, Object>> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientUsersRoleMappingBaseUrl+"/"+userId+"/role-mappings/clients/"+clientId, HttpMethod.POST, requestBody, accessToken, null);
    }
}
