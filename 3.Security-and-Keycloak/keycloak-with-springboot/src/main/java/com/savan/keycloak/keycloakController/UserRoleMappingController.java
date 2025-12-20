package com.savan.keycloak.keycloakController;

import com.savan.keycloak.responseDto.ResponseDto;
import com.savan.keycloak.service.CommonHelper;
import com.savan.keycloak.service.RestTemplateService;
import com.savan.keycloak.channelApis.ChannelApisService;
import com.savan.keycloak.tenantMstApis.TenantMstApisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
keycloak.admin.user.roles.mapping.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/users/{{user-id}}/role-mappings
*/

@RestController
@RequestMapping("/keycloak/users/{userId}/role-mappings")
public class UserRoleMappingController {

    @Value("${keycloak.admin.user.roles.mapping.base.url}")
    private String usersRoleMappingBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private ChannelApisService channelApisService;

    @Autowired
    private TenantMstApisService tenantMstApisService;

    // Get role mappings
    @GetMapping("")
    public ResponseEntity<String> getClientRoles(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings", HttpMethod.GET, null, accessToken, null);
    }

    // Get realm-level roles that can be mapped
    @GetMapping("/realm/available")
    public ResponseEntity<String> getAvailableRoles(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm/available", HttpMethod.GET, null, accessToken, null);
    }

    // Get effective realm-level role mappings
    @GetMapping("/realm/composite")
    public ResponseEntity<String> getEffectiveRoles(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm/composite", HttpMethod.GET, null, accessToken, null);
    }

    // Get realm-level role mappings
    @GetMapping("/realm")
    public ResponseEntity<String> getRealmLevelRoles(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm", HttpMethod.GET, null, accessToken, null);
    }

    // Add realm-level role mappings to the user(i.e. channel)
    @PostMapping("/realm")
    public ResponseEntity<ResponseDto> addRealmLevelRolesToUser(@PathVariable String userId, @RequestBody List<Map<String, Object>> requestBodyList) {

        // For Keycloak
        List<Map<String, Object>> keycloakUserRoleMappingList = new ArrayList<>();
        // For Integration: Buy-Apis
        List<Map<String, Object>> intgBuyApisMapList = new ArrayList<>();
        // For Integration: Assign-Roles(i.e.apis)
        List<Map<String, Object>> intgAssignApisRolesMapList = new ArrayList<>();

        // Loop through request body
        for (Map<String, Object> requestBody : requestBodyList) {
            // Keycloak
            Map<String, Object> keycloakUserRoleMapping = new HashMap<>();
            keycloakUserRoleMapping.put("id", requestBody.get("keycloakRoleId").toString());
            keycloakUserRoleMapping.put("name", requestBody.get("serviceCode").toString());
            keycloakUserRoleMappingList.add(keycloakUserRoleMapping);

            // Intg
            Map<String, Object> intgBuyApisMap = new HashMap<>();
            Map<String, Object> intgAssignApisRolesMap = new HashMap<>();
            if(requestBody.containsKey("noOfHits")){
                intgBuyApisMap.put("serviceCode", requestBody.get("serviceCode").toString());
                intgBuyApisMap.put("noOfHits", Integer.parseInt(requestBody.get("noOfHits").toString()));
                intgBuyApisMap.put("tenantMaster", requestBody.get("tenantMaster").toString());
                intgBuyApisMap.put("channelName", requestBody.get("channelName").toString());
                intgBuyApisMapList.add(intgBuyApisMap);
            }

            intgAssignApisRolesMap.put("serviceCode", requestBody.get("serviceCode").toString());
            intgAssignApisRolesMap.put("channelName", requestBody.get("channelName").toString());
            intgAssignApisRolesMap.put("tenantMaster", requestBody.get("tenantMaster").toString());
            intgAssignApisRolesMapList.add(intgAssignApisRolesMap);
        }

        System.out.println("intgBuyApisMapList: "+intgBuyApisMapList);
        System.out.println("intgAssignApisRolesMapList: "+intgAssignApisRolesMapList);

        ResponseEntity<Object> integrationResponse;
        try {
            // Assign roles to user in integration
            String successMessage1 = tenantMstApisService.buyApis(intgBuyApisMapList);
            String successMessage2 = channelApisService.assignApisRoles(intgAssignApisRolesMapList);
            integrationResponse =  ResponseEntity.ok(successMessage1 + ", " + successMessage2);
        } catch (Exception e) {
            // log.info(e.getMessage());
            integrationResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Assign roles to user in keycloak
        String accessToken = commonHelper.getAccessToken();
        ResponseEntity<String> keycloakResponse = restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm", HttpMethod.POST, keycloakUserRoleMappingList, accessToken, null);

        return ResponseEntity.ok(new ResponseDto(integrationResponse, keycloakResponse));
    }

    // Delete realm-level role mappings from the user(i.e. channel)
    @DeleteMapping("/realm")
    public ResponseEntity<ResponseDto> deleteRealmLevelRolesFromUser(@PathVariable String userId, @RequestBody List<Map<String, Object>> requestBodyList) {

        // For Keycloak
        List<Map<String, Object>> keycloakUserRoleMappingList = new ArrayList<>();
        // For Integration
        List<Map<String, Object>> intgUserRoleMappingList = new ArrayList<>();
        for (Map<String, Object> requestBody : requestBodyList) {
            // Keycloak
            Map<String, Object> keycloakUserRoleMapping = new HashMap<>();
            keycloakUserRoleMapping.put("id", requestBody.get("keycloakRoleId").toString());
            keycloakUserRoleMapping.put("name", requestBody.get("serviceCode").toString());
            keycloakUserRoleMappingList.add(keycloakUserRoleMapping);

            // Intg
            Map<String, Object> intgUserRoleMap = new HashMap<>();
            intgUserRoleMap.put("serviceCode", requestBody.get("serviceCode").toString());
            intgUserRoleMap.put("channelName", requestBody.get("channelName").toString());
            intgUserRoleMap.put("tenantMaster", requestBody.get("tenantMaster").toString());
            intgUserRoleMappingList.add(intgUserRoleMap);
        }

        ResponseEntity<Object> integrationResponse;
        try {
            // Un-Assign roles to user in integration
            integrationResponse =  ResponseEntity.ok(channelApisService.unAssignApisRoles(intgUserRoleMappingList));
        } catch (Exception e) {
            // log.info(e.getMessage());
            integrationResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Un-Assign roles to user in keycloak
        String accessToken = commonHelper.getAccessToken();
        ResponseEntity<String> keycloakResponse = restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm", HttpMethod.DELETE, keycloakUserRoleMappingList, accessToken, null);

        return ResponseEntity.ok(new ResponseDto(integrationResponse, keycloakResponse));
    }

    // Add realm roles mappings to the channel
    @PostMapping("/realm/assign-role-to-channel")
    public ResponseEntity<String> addRealmRolesToChannel(@PathVariable String userId, @RequestBody List<Map<String, Object>> requestBodyList) {
        // Assign roles to user in keycloak
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersRoleMappingBaseUrl+"/"+userId+"/role-mappings/realm", HttpMethod.POST, requestBodyList, accessToken, null);
    }
}
