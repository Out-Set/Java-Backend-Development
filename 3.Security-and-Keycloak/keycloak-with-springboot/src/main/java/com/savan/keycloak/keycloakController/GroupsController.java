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
keycloak.admin.group.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/groups
*/

@RestController
@RequestMapping("/keycloak/groups")
public class GroupsController {

    @Value("${keycloak.admin.group.base.url}")
    private String groupsBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    // Returns the groups counts
    @GetMapping("/count")
    public ResponseEntity<String> getClients() {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/count", HttpMethod.GET, null, accessToken, null);
    }

    // Get group hierarchy
    @GetMapping("")
    public ResponseEntity<String> getGroupsHierarchy() {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl, HttpMethod.GET, null, accessToken, null);
    }

    // Get group by id
    @GetMapping("/{groupId}")
    public ResponseEntity<String> getGroupsById(@PathVariable String groupId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId, HttpMethod.GET, null, accessToken, null);
    }

    // Create group
    @PostMapping("")
    public ResponseEntity<String> crateGroups(@RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl, HttpMethod.POST, requestBody, accessToken, null);
    }

    // Update group, ignores subgroups
    @PutMapping("/{groupId}")
    public ResponseEntity<String> updateGroupsById(@PathVariable String groupId, @RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId, HttpMethod.PUT, requestBody, accessToken, null);
    }

    // Delete group by id
    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroupsById(@PathVariable String groupId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId, HttpMethod.DELETE, null, accessToken, null);
    }

    // Get users associated with group
    @GetMapping("/{groupId}/members")
    public ResponseEntity<String> usersAssociatedWithGroups(@PathVariable String groupId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId+"/members", HttpMethod.GET, null, accessToken, null);
    }

    // Return a paginated list of subgroups
    @GetMapping("/{groupId}/children")
    public ResponseEntity<String> listOfSubgroups(@PathVariable String groupId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId+"/children", HttpMethod.GET, null, accessToken, null);
    }

    // Set or create child
    @PostMapping("/{groupId}/children")
    public ResponseEntity<String> setOrCreateSubgroups(@PathVariable String groupId, @RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(groupsBaseUrl+"/"+groupId+"/children", HttpMethod.POST, requestBody, accessToken, null);
    }

}
