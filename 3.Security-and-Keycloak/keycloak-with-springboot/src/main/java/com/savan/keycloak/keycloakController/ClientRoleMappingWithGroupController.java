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
keycloak.admin.client.roles.mapping.with.group.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/groups/{{group-id}}/role-mappings/clients/{{client-uuid}}
*/

@RestController
@RequestMapping("/keycloak/groups/{groupId}/role-mappings/clients/{clientId}")
public class ClientRoleMappingWithGroupController {

    @Value("${keycloak.admin.client.roles.mapping.with.group.base.url}")
    private String clientGroupsRoleMappingBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Get available client-level roles
    @GetMapping("/available")
    public ResponseEntity<String> getAvailableRoles(@PathVariable String groupId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientGroupsRoleMappingBaseUrl+"/"+groupId+"/role-mappings/clients/"+clientId+"/available", HttpMethod.GET, null, accessToken, null);
    }

    // Get effective client-level role mappings
    @GetMapping("/composite")
    public ResponseEntity<String> getEffectiveRoles(@PathVariable String groupId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientGroupsRoleMappingBaseUrl+"/"+groupId+"/role-mappings/clients/"+clientId+"/composite", HttpMethod.GET, null, accessToken, null);
    }

    // Delete client-level roles from group
    @DeleteMapping("")
    public ResponseEntity<String> deleteEffectiveRoles(@PathVariable String groupId, @PathVariable String clientId, @RequestBody List<Map<String, Object>> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientGroupsRoleMappingBaseUrl+"/"+groupId+"/role-mappings/clients/"+clientId, HttpMethod.DELETE, requestBody, accessToken, null);
    }

    // Get client-level role mappings for the group
    @GetMapping("")
    public ResponseEntity<String> getClientLevelRoles(@PathVariable String groupId, @PathVariable String clientId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientGroupsRoleMappingBaseUrl+"/"+groupId+"/role-mappings/clients/"+clientId, HttpMethod.GET, null, accessToken, null);
    }

    // Add client-level roles to the group role mapping
    @PostMapping("")
    public ResponseEntity<String> getClientLevelRolesToUser(@PathVariable String groupId, @PathVariable String clientId, @RequestBody List<Map<String, Object>> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(clientGroupsRoleMappingBaseUrl+"/"+groupId+"/role-mappings/clients/"+clientId, HttpMethod.POST, requestBody, accessToken, null);
    }
}
