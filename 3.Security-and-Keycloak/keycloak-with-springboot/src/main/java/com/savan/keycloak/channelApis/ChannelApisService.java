package com.savan.keycloak.channelApis;

import com.savan.keycloak.channel.Channel;
import com.savan.keycloak.channel.ChannelService;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifierService;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.tenantMaster.TenantMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChannelApisService {

    @Autowired
    private TenantMasterService tenantMasterService;

    @Autowired
    private ServiceIdentifierService serviceIdentifierService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelApisRepo channelApisRepo;

    // Assign Apis-Roles to Channel
    public String assignApisRoles(List<Map<String, Object>> channelNamesAndServiceCodesList) {

        for (Map<String, Object> channelNamesAndServiceCodes : channelNamesAndServiceCodesList) {
            // Extracting values from the map
            String serviceCode = channelNamesAndServiceCodes.get("serviceCode").toString();
            String channelName = channelNamesAndServiceCodes.get("channelName").toString();
            String tenantMaster = channelNamesAndServiceCodes.get("tenantMaster").toString();
            // log.info("Api-Name :: {}, User-Name ::  {}, Source-System :: {}", serviceCode, userName, sourceSystem);
            System.out.println(serviceCode+", "+channelName+", "+tenantMaster);

            // Fetching related entities
            TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
            if(existingTenantMaster != null){
                // Fetching related entities
                ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
                if(serviceIdentifier != null){
                    // Fetching related entities
                    Channel channel = channelService.findByChannelNameAndTenantMasterId(channelName, existingTenantMaster.getId());
                    if(channel != null){
                        // Checking if the user already has this API-Role
                        ChannelApis existingChannelApis = channelApisRepo.findByServiceIdentifierAndTenantMasterIdAndChannel(channel.getId(), serviceIdentifier.getId(), existingTenantMaster.getId());

                        if (existingChannelApis == null) {
                            // Creating a new entry if it doesn't exist
                            ChannelApis newUserApis = new ChannelApis();
                            newUserApis.setChannel(channel);
                            newUserApis.setConsumedHitsOfApi(0);
                            newUserApis.setServiceIdentifier(serviceIdentifier);
                            newUserApis.setTenantMaster(existingTenantMaster);
                            channelApisRepo.save(newUserApis);

                            // log.info("Role assigned successfully: {}", serviceCode);
                            System.out.println("Role assigned successfully");
                        } else {
                            // entry already exists
                            // log.info("Role already exists: {}", serviceCode);
                            System.out.println("entry already exists: "+existingChannelApis);
                        }
                    } else {
                        // log.info("User with username: {}, does not exits in the source-system {}.", channelName, tenantMaster);
                        return "User with username: " + channelName + ", does not exits in the source-system "+tenantMaster;
                    }
                } else {
                    // log.info("Service with code: {}, does not exits!", serviceCode);
                    return "Service with code: " + serviceCode + ", does not exits!";
                }
            } else {
                // log.info("Source-System with code: {}, does not exits!", tenantMaster);
                return "Source-System with code: " + tenantMaster + ", does not exits!";
            }
        }
        // log.info("Roles assigned successfully!");
        return "Roles assigned successfully!";
    }

    // Un-Assign Apis-Roles to Channel
    public String unAssignApisRoles(List<Map<String, Object>> channelNamesAndServiceCodesList) {

        for (Map<String, Object> channelNamesAndServiceCodes : channelNamesAndServiceCodesList) {
            // Extracting values from the map
            String serviceCode = channelNamesAndServiceCodes.get("serviceCode").toString();
            String channelName = channelNamesAndServiceCodes.get("channelName").toString();
            String tenantMaster = channelNamesAndServiceCodes.get("tenantMaster").toString();
            // log.info("Api-Name :: {}, User-Name ::  {}, Source-System :: {}", serviceCode, channelName, tenantMaster);

            // Fetching related entities
            TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
            if(existingTenantMaster != null){
                // Fetching related entities
                ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
                if(serviceIdentifier != null){
                    // Fetching related entities
                    Channel channel = channelService.findByChannelNameAndTenantMasterId(channelName, existingTenantMaster.getId());
                    if(channel != null){
                        // Checking if the user already has this API-Role
                        ChannelApis existingChannelApis = channelApisRepo.findByServiceIdentifierAndTenantMasterIdAndChannel(channel.getId(), serviceIdentifier.getId(), existingTenantMaster.getId());

                        if (existingChannelApis != null) {
                            // Deleting entry if it already exists
                            channelApisRepo.delete(existingChannelApis);
                            // log.info("Role un-assigned successfully: {}", serviceCode);
                        } else {
                            // entry doesn't exists
                            // log.info("Role doesn't exists: {}", serviceCode);
                        }
                    } else {
                        // log.info("User with username: {}, does not exits!", channelName);
                        return "User with username: " + channelName + ", does not exits!";
                    }
                } else {
                    // log.info("Service with code: {}, does not exits!", serviceCode);
                    return "Service with code: " + serviceCode + ", does not exits!";
                }
            } else {
                // log.info("Source-System with code: {}, does not exits!", tenantMaster);
                return "Source-System with code: " + tenantMaster + ", does not exits!";
            }
        }
        // log.info("Roles un-assigned successfully!");
        return "Roles un-assigned successfully!";
    }

    // Get Services assigned to Channel with User-Id
    public List<String> getAssignedApisRolesToChannel(String channelName, String tenantMaster) {

        // Fetching related entities
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null){
            // Fetching related entities
            Channel user = channelService.findByChannelNameAndTenantMaster(channelName, tenantMaster);
            if(user != null){
                // Fetching related entities
                return channelApisRepo.servicesAssignedToChannel(user.getId(), existingTenantMaster.getId());
            } else {
                // log.info("User with username: {}, does not exits in source-system: {}", channelName, tenantMaster);
                return Collections.emptyList();
            }
        } else {
            // log.info("Source-System with code: {}, does not exits!", tenantMaster);
            return Collections.emptyList();
        }
    }

    // Get Assigned Apis-Roles to Tenant with Service-Name
    public List<String> getAssignedApisRolesWithService(String serviceCode, String tenantMaster) {

        // Fetching related entities
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null){
            // Fetching related entities
            ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
            if(serviceIdentifier != null){
                // Fetching related entities
                return channelApisRepo.channelsAssociatedWithService(serviceIdentifier.getId(), existingTenantMaster.getId());
            } else {
                // log.info("Service with service-code: {}, does not exits!", serviceCode);
                return Collections.emptyList();
            }
        } else {
            // log.info("Source-System with code: {}, does not exits!", tenantMaster);
            return Collections.emptyList();
        }
    }

    // Update user consume-hit-api count
    public void updateUserApisConsumeHitCount(String serviceCode, String tenantMaster, String channelName){
        try {
            ChannelApis channelApis = channelApisRepo.findByServiceIdentifierAndTenantMasterAndChannel(serviceCode, tenantMaster, channelName);
            channelApis.setConsumedHitsOfApi(channelApis.getConsumedHitsOfApi() + 1);
            channelApisRepo.save(channelApis);
            // log.info("consume-api-hit count updated for the user: {}, present in source-system: {}, on consuming the service: {}", channelName, tenantMaster, serviceCode);
        } catch(Exception e) {
            // log.info("An exception: {} occurred while updating consume-hit-count for the user: {}, present in source-system: {}, on consuming the service: {}", e.getMessage(), channelName, tenantMaster, serviceCode);
        }
    }

}
