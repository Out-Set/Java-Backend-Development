package com.savan.keycloak.keycloakController;

import com.savan.keycloak.responseDto.ResponseDto;
import com.savan.keycloak.service.CommonHelper;
import com.savan.keycloak.service.RestTemplateService;
import com.savan.keycloak.channel.Channel;
import com.savan.keycloak.channel.ChannelService;
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
keycloak.admin.users.base.url = ${keycloak-base-url}/admin/realms/${keycloak.realm}/users
*/

@RestController
@RequestMapping("/keycloak/users")
public class UserController {

    @Value("${keycloak.admin.users.base.url}")
    private String usersBaseUrl;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private CommonHelper commonHelper;

    @Autowired
    private ChannelService channelService;

    // Get users in a realm
    @GetMapping("")
    public ResponseEntity<String> getUsers(@RequestParam(required = false) Map<String, Object> queryParams) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersBaseUrl, HttpMethod.GET, null, accessToken, queryParams);
    }

    // Get user-by-id
    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId, HttpMethod.GET, null, accessToken, null);
    }

    // Create new-user
    @PostMapping("/tenant-master/{tenantMaster}")
    public ResponseEntity<ResponseDto> createNewUser(@RequestBody Map<String, Object> requestBody, @PathVariable String tenantMaster) {

        ResponseEntity<Object> integrationResponse;
        try {
            // Create user in integration
            Channel user = new Channel();
            user.setFirstName(requestBody.get("firstName").toString());
            user.setLastName(requestBody.get("lastName").toString());
            user.setUsername(requestBody.get("username").toString().toLowerCase());
            user.setEmail(requestBody.get("email").toString());
            user.setIsEmailVerified("true");
            user.setPassword(requestBody.get("password").toString());
            user.setScope("USER"); // Initially
            integrationResponse =  ResponseEntity.ok(channelService.createChannel(user, tenantMaster));
        } catch (Exception e) {
            // log.info(e.getMessage());
            integrationResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Prepare request-body for keycloak
        ResponseEntity<String> keycloakResponse;
        Map<String, Object> keycloakRequestBody = new HashMap<>();
        List<Map<String, Object>> credentials = new ArrayList<>();
        try {
            keycloakRequestBody.put("username", requestBody.get("username"));
            keycloakRequestBody.put("firstName", requestBody.get("firstName"));
            keycloakRequestBody.put("lastName", requestBody.get("lastName"));
            keycloakRequestBody.put("email", requestBody.get("email"));
            keycloakRequestBody.put("emailVerified", true);
            keycloakRequestBody.put("enabled", true);

            Map<String, Object> credentialMap = new HashMap<>();
            credentialMap.put("type", "password");
            credentialMap.put("value", requestBody.get("password"));
            credentialMap.put("temporary", false);
            credentials.add(credentialMap);

            keycloakRequestBody.put("credentials", credentials);

            // Create user in Keycloak
            String accessToken = commonHelper.getAccessToken();
            keycloakResponse = restTemplateService.hitKeycloakApi(usersBaseUrl, HttpMethod.POST, keycloakRequestBody, accessToken, null);
        } catch(Exception e) {
            // log.info(e.getMessage());
            keycloakResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok(new ResponseDto(integrationResponse, keycloakResponse));
    }

    // Delete user-by-id
    @DeleteMapping("/keycloak-user-id/{userId}/tenant-master/{tenantMaster}/channel-name/{channelName}")
    public ResponseEntity<ResponseDto> deleteUserById(@PathVariable String userId, @PathVariable String tenantMaster, @PathVariable String channelName) {
        // Delete from integration
        ResponseEntity<Object> integrationResponse;
        try {
            integrationResponse =  ResponseEntity.ok(channelService.deleteChannel(tenantMaster, channelName));
        } catch (Exception e) {
            // log.info(e.getMessage());
            integrationResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Delete from keycloak
        String accessToken = commonHelper.getAccessToken();
        ResponseEntity<String> keycloakResponse = restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId, HttpMethod.DELETE, null, accessToken, null);

        return ResponseEntity.ok(new ResponseDto(integrationResponse, keycloakResponse));
    }

    // Update user-by-id
    @PutMapping("/keycloak-user-id/{userId}/tenant-master/{tenantMaster}")
    public ResponseEntity<ResponseDto> updateUserById(@PathVariable String userId, @PathVariable String tenantMaster, @RequestBody Map<String, Object> requestBody) {

        ResponseEntity<Object> integrationResponse;
        try {
            // Update user in integration
            Map<String, String> user = new HashMap<>();
            user.put("username", requestBody.get("username").toString().toLowerCase());
            user.put("tenantMaster", tenantMaster);
            user.put("firstName", requestBody.get("firstName").toString());
            user.put("lastName", requestBody.get("lastName").toString());
            user.put("email", requestBody.get("email").toString());
            user.put("isEmailVerified", "true");
            user.put("password", requestBody.get("password").toString());
            integrationResponse =  ResponseEntity.ok(channelService.updateChannel(user));
        } catch (Exception e) {
            // log.info(e.getMessage());
            integrationResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        // Prepare request-body for keycloak
        ResponseEntity<String> keycloakResponse;
        Map<String, Object> keycloakRequestBody = new HashMap<>();
        List<Map<String, Object>> credentials = new ArrayList<>();
        try {
            keycloakRequestBody.put("username", requestBody.get("username"));
            keycloakRequestBody.put("firstName", requestBody.get("firstName"));
            keycloakRequestBody.put("lastName", requestBody.get("lastName"));
            keycloakRequestBody.put("email", requestBody.get("email"));
            keycloakRequestBody.put("emailVerified", true);
            keycloakRequestBody.put("enabled", true);

            Map<String, Object> credentialMap = new HashMap<>();
            credentialMap.put("type", "password");
            credentialMap.put("value", requestBody.get("password"));
            credentialMap.put("temporary", false);
            credentials.add(credentialMap);

            keycloakRequestBody.put("credentials", credentials);

            // Update user in Keycloak
            String accessToken = commonHelper.getAccessToken();
            keycloakResponse = restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId, HttpMethod.PUT, keycloakRequestBody, accessToken, null);
        } catch(Exception e) {
            // log.info(e.getMessage());
            keycloakResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok(new ResponseDto(integrationResponse, keycloakResponse));
    }

    // Reset user-password
    @PutMapping("/{userId}/reset-password")
    public ResponseEntity<String> resetUserPassword(@PathVariable String userId, @RequestBody Map<String, Object> requestBody) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId+"/reset-password", HttpMethod.PUT, requestBody, accessToken, null);
    }

    // Get sessions-associated with user
    @GetMapping("/{userId}/sessions")
    public ResponseEntity<String> userSessions(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId+"/sessions", HttpMethod.GET, null, accessToken, null);
    }

    // Remove/logout from all the sessions-associated with user
    @PostMapping("/{userId}/logout")
    public ResponseEntity<String> userSessionsRemove(@PathVariable String userId) {
        String accessToken = commonHelper.getAccessToken();
        return restTemplateService.hitKeycloakApi(usersBaseUrl+"/"+userId+"/logout", HttpMethod.POST, null, accessToken, null);
    }
}
