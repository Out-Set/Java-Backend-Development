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
keycloak.admin.client.roles.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/clients/{{client-uuid}}/roles
*/

@RestController
@RequestMapping("/keycloak/clients/{clientId}/roles")
public class ClientRolesController {

    @Value("${keycloak.admin.client.roles.base.url}")
    private String clientRolesBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Get all roles of the client
    @GetMapping("")
    public ResponseEntity<String> getClientRoles(@PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientRolesBaseUrl+"/"+clientId+"/roles", HttpMethod.GET, null, accessToken, null);
    }

    // Get a client role by name
    @GetMapping("/{role}")
    public ResponseEntity<String> getClientRolesByName(@PathVariable String clientId, @PathVariable String role) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientRolesBaseUrl+"/"+clientId+"/roles/"+role, HttpMethod.GET, null, accessToken, null);
    }

    // Create a new role for the client
    @PostMapping("")
    public ResponseEntity<String> createClientRoles(@PathVariable String clientId, @RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientRolesBaseUrl+"/"+clientId+"/roles", HttpMethod.POST, requestBody, accessToken, null);
    }

    // Delete a role of client by name
    @DeleteMapping("/{role}")
    public ResponseEntity<String> deleteClientRoles(@PathVariable String clientId, @PathVariable String role) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientRolesBaseUrl+"/"+clientId+"/roles/"+role, HttpMethod.DELETE, null, accessToken, null);
    }

}
