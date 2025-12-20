package com.savan.keycloak.tenantMstApis;

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
@RequestMapping("/api/tenant-master-apis")
public class TenantMstApisController {

    @Autowired
    private TenantMstApisService tenantMstApisService;

    // Buy apis
    @PostMapping("/buy")
    public ResponseEntity<String> buyApis(@RequestBody List<Map<String, Object>> apisWithNoOfHitsList) {
        try {
            String successMessage = tenantMstApisService.buyApis(apisWithNoOfHitsList);
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get all Tenant-Master-Apis
    @GetMapping("/apis-mappings")
    public ResponseEntity<List<Map<String, Object>>> getAllTenantMasterApis() {
        try {
            List<Map<String, Object>> allTenantMasterApis = tenantMstApisService.getAllTenantMasterApis();
            return ResponseEntity.ok(allTenantMasterApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Tenant-Master-Apis Details with Tenant-Master
    @GetMapping("/apis-mappings/tenant-master/{tenantMaster}")
    public ResponseEntity<List<Map<String, Object>>> findBySourceSystemId(@PathVariable String tenantMaster) {
        try {
            List<Map<String, Object>> tenantMasterApis = tenantMstApisService.findByTenantMaster(tenantMaster);
            return ResponseEntity.ok(tenantMasterApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Tenant-Master-Apis Details with Service-Code
    @GetMapping("/apis-mappings/service-code/{serviceCode}")
    public ResponseEntity<List<Map<String, Object>>> findByServiceIdentifierId(@PathVariable String serviceCode) {
        try {
            List<Map<String, Object>> tenantMasterApis = tenantMstApisService.findByServiceCode(serviceCode);
            return ResponseEntity.ok(tenantMasterApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Tenant-Master-Apis with Service-Code and Tenant-Master
    @GetMapping("/apis-mappings/tenant-master/{tenantMaster}/service-code/{serviceCode}")
    public ResponseEntity<Map<String, Object>> findByServiceIdentifierIdAndSourceSystemId(@PathVariable String tenantMaster, @PathVariable String serviceCode) {
        try {
            Map<String, Object> tenantMasterApis = tenantMstApisService.findByServiceCodeAndTenantMaster(tenantMaster, serviceCode);
            return ResponseEntity.ok(tenantMasterApis);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Find Services associated with tenant-master
    @GetMapping("/tenant-master/{tenantMaster}/subscribed-services")
    public ResponseEntity<List<String>> subscribedServicesOfTenantMaster(@PathVariable String tenantMaster){
        try {
            List<String> successMessage = tenantMstApisService.subscribedServicesOfTenantMaster(tenantMaster);
            return ResponseEntity.status(HttpStatus.OK).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Not working: correct it
    // Find Source-systems associated with Service
    @GetMapping("/service-code/{serviceCode}/associated-source-systems")
    public ResponseEntity<List<String>> servicesSubscribedBySourceSystems(String serviceCode){
        try {
            List<String> successMessage = tenantMstApisService.servicesSubscribedByTenantMasters(serviceCode);
            return ResponseEntity.status(HttpStatus.OK).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get sum of remaining & consumed Hits
    @GetMapping("/tenant-master/{tenantMaster}/get-api-hits-sum")
    public Map<String, Object> getApisHitsSum(@PathVariable String tenantMaster){
        return tenantMstApisService.getApisHitsSum(tenantMaster);
    }

    // Get Apis Failed & Success Hits Sum
    @GetMapping("/tenant-master/{tenantMaster}/get-success-failed-hits-sum")
    public Map<String, Object> getApisFailedAndSuccessHitsSum(@PathVariable String tenantMaster){
        return tenantMstApisService.getApisFailedAndSuccessHitsSum(tenantMaster);
    }

    // Consume/Decrease Api-Hit Count
    @GetMapping("/consume")
    public ResponseEntity<String> consumeApi(@RequestParam String serviceCode, @RequestParam String tenantMaster) {
        try {
            String successMessage = tenantMstApisService.consumeApi(serviceCode, tenantMaster);
            return ResponseEntity.status(HttpStatus.OK).body(successMessage);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
