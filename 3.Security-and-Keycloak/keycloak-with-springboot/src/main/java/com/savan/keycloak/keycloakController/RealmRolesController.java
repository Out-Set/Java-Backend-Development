package com.savan.keycloak.keycloakController;

import com.savan.keycloak.service.CommonHelper;
import com.savan.keycloak.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/keycloak/realm/roles")
public class RealmRolesController {

    @Value("${keycloak.admin.realm.roles.base.url}")
    private String realmRolesBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Get all roles of the realm
    @GetMapping("")
    public ResponseEntity<String> getRealmRoles() {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(realmRolesBaseUrl, HttpMethod.GET, null, accessToken, null);
    }

    // Get a realm role by name
    @GetMapping("/{role}")
    public ResponseEntity<String> getRealmRolesByName(@PathVariable String role) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(realmRolesBaseUrl+"/"+role, HttpMethod.GET, null, accessToken, null);
    }

    // Create a new role for the realm
    @PostMapping("")
    public ResponseEntity<String> createRealmRoles(@RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(realmRolesBaseUrl, HttpMethod.POST, requestBody, accessToken, null);
    }

    // Delete a role of realm by name
    @DeleteMapping("/{role}")
    public ResponseEntity<String> deleteRealmRoles(@PathVariable String role) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(realmRolesBaseUrl+"/"+role, HttpMethod.DELETE, null, accessToken, null);
    }

}
