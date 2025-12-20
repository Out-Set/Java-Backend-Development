package com.savan.keycloak.channelApis;

import com.savan.keycloak.tenantMstApis.TenantMstApisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ChannelApisController {

    @Autowired
    private ChannelApisService channelApisService;

    @Autowired
    private TenantMstApisService tenantMstApisService;

    // Buy Apis and Assign Roles to Channel
    @PostMapping("/channel/services/buy-and-assign")
    public ResponseEntity<String> buyApiAndAssign(@RequestBody List<Map<String, Object>> servicesWithNoOfHits){
        try {
            String successMessage1 = tenantMstApisService.buyApis(servicesWithNoOfHits);
            String successMessage2 = channelApisService.assignApisRoles(servicesWithNoOfHits);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage1+", "+successMessage2);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Assign Apis-Roles to Channel
    @PostMapping("channel/roles/assign")
    public ResponseEntity<String> assignApisRoles(@RequestBody List<Map<String, Object>> channelNamesAndServiceCodesList) {
        try {
            String successMessage = channelApisService.assignApisRoles(channelNamesAndServiceCodesList);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Assign Apis-Roles to Channel
    @PostMapping("channel/roles/un-assign")
    public ResponseEntity<String> unAssignApisRoles(@RequestBody List<Map<String, Object>> channelNamesAndServiceCodesList) {
        try {
            String successMessage = channelApisService.unAssignApisRoles(channelNamesAndServiceCodesList);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get Assigned Apis-Roles with source-system & channel
    @GetMapping("/apis-assigned/tenant-master/{tenantMaster}/channel-name/{channelName}")
    public ResponseEntity<List<String>> getAssignedApisRolesToChannel(@PathVariable String tenantMaster, @PathVariable String channelName) {
        try {
            List<String> channelApis = channelApisService.getAssignedApisRolesToChannel(channelName, tenantMaster);
            return ResponseEntity.ok(channelApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Assigned Apis-Roles with source-system & service-code
    @GetMapping("/apis-assigned/tenant-master/{tenantMaster}/service-code/{serviceCode}")
    public ResponseEntity<List<String>> getChannelApisWithServiceIdentifierCode(@PathVariable String tenantMaster, @PathVariable String serviceCode) {
        try {
            List<String> userApis = channelApisService.getAssignedApisRolesWithService(serviceCode, tenantMaster);
            return ResponseEntity.ok(userApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
