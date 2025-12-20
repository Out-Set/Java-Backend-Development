package com.savan.keycloak.tenantMstApis;

import com.savan.keycloak.channel.Channel;
import com.savan.keycloak.channel.ChannelService;
import com.savan.keycloak.emRepository.EntityManagerRepo;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifierService;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.tenantMaster.TenantMasterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TenantMstApisService {

    @Autowired
    private TenantMasterService tenantMasterService;

    @Autowired
    private ServiceIdentifierService serviceIdentifierService;

    @Autowired
    private TenantMstApisRepo tenantMstApisRepo;

    @Autowired
    private EntityManagerRepo entityManagerRepo;

    @Autowired
    private ChannelService channelService;

    // Buy Apis
    public String buyApis(List<Map<String, Object>> servicesWithNoOfHitsList) {

        for (Map<String, Object> servicesWithNoOfHits : servicesWithNoOfHitsList) {
            // Extracting values from the map
            String serviceCode = servicesWithNoOfHits.get("serviceCode").toString();
            int noOfHits = Integer.parseInt(servicesWithNoOfHits.get("noOfHits").toString());
            String tenantMaster = servicesWithNoOfHits.get("tenantMaster").toString();
            String channelName = servicesWithNoOfHits.get("channelName").toString();
            // log.info("Source-Name :: {}, Service-Name ::  {}, No of Hits :: {}", tenantMaster, serviceCode, noOfHits);

            // Fetching related entities
            TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
            if(existingTenantMaster != null) {
                // Fetching related entities
                ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
                if(serviceIdentifier != null) {
                    // Fetching related entities
                    Channel existingChannel = channelService.findByChannelNameAndTenantMaster(channelName, tenantMaster);
                    if(existingChannel != null){
                        // Checking if the channel with Tenant-Mst already has this API
                        TenantMstApis existingTenantMstAndChannelApis = tenantMstApisRepo
                                .findByServiceIdentifierAndTenantMasterAndChannel(serviceIdentifier, existingTenantMaster, existingChannel);

                        if (existingTenantMstAndChannelApis == null) {
                            // Creating a new entry if it doesn't exist
                            TenantMstApis newTenantMstApis = new TenantMstApis();
                            newTenantMstApis.setServiceIdentifier(serviceIdentifier);
                            newTenantMstApis.setTenantMaster(existingTenantMaster);
                            newTenantMstApis.setChannel(existingChannel);
                            newTenantMstApis.setRemainingHitsOfApi(noOfHits);
                            newTenantMstApis.setConsumedHitsOfApi(0);
                            tenantMstApisRepo.save(newTenantMstApis);
                            // log.info("New Source-System-Apis entry created: ", newTenantMstApis);
                            System.out.println("creating new");
                        } else {
                            // Updating remaining hits if the entry already exists
                            existingTenantMstAndChannelApis
                                    .setRemainingHitsOfApi(existingTenantMstAndChannelApis.getRemainingHitsOfApi() + noOfHits);
                            tenantMstApisRepo.save(existingTenantMstAndChannelApis);
                            // log.info("Existing Source-System-Apis entry updated: {}", existingSourceSystemApis);
                            System.out.println("updated hits");
                        }
                    } else {
                        // log.info("Channel: {}, with the tenant-master: {}, does not exists!", channelName, tenantMaster);
                        return "Channel- "+channelName+", with the tenant-master: "+ tenantMaster + ", does not exists!";
                    }
                } else {
                    // log.info("Service with the code: {}, does not exists!", serviceCode);
                    return "Service with the code: "+ serviceCode + ", does not exists!";
                }
            } else {
                // log.info("Source-System with the code: {}, does not exists!", tenantMaster);
                return "Source-System with the code: "+ tenantMaster + ", does not exists!";
            }

        }
        // log.info("Thanks for choosing our service!");
        return "Thanks for choosing our service!";
    }



    // Get all Tenant-Master-Apis
    public List<Map<String, Object>> getAllTenantMasterApis(){
        return entityManagerRepo.getAllTenantMasterApis();
    }

    // Get Tenant-Master-Apis Details with Tenant-Master
    public List<Map<String, Object>> findByTenantMaster(String tenantMaster) {
        return entityManagerRepo.findByTenantMaster(tenantMaster);
    }

    // Get Tenant-Master-Apis Details with Service-Code
    public List<Map<String, Object>> findByServiceCode(String serviceCode) {
        return entityManagerRepo.findByServiceCode(serviceCode);
    }

    // Get Tenant-Master-Apis with Service-Code and Tenant-Master
    public Map<String, Object> findByServiceCodeAndTenantMaster(String tenantMaster, String serviceCode) {
        return entityManagerRepo.findByServiceCodeAndTenantMaster(tenantMaster, serviceCode);
    }

    // Check Service Subscription & remaining hits
    public boolean validateServiceSubscription(Exchange exchange, String serviceCode, String tenantMaster) {

        // Fetching related entities
        ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);

        // Checking if the user already has this API
        TenantMstApis existingTenantMstApis = tenantMstApisRepo
                .findByServiceIdentifierAndTenantMaster(serviceIdentifier, existingTenantMaster);

        if (existingTenantMstApis == null) {
            // log.info("You do not have subscription for this service, please get it first!");
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.setException(new IllegalArgumentException("You do not have subscription for this service, please get it first!"));
            return false;
        } else {
            int remainingHitConuts = existingTenantMstApis.getRemainingHitsOfApi();
            if(remainingHitConuts == 0) {
                // log.info("You have consumed all your hits for this servie, please get it first!");
                exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
                exchange.setException(new IllegalArgumentException("You have consumed all your hits for this servie, please get it first!"));
                return false;
            } else {
                return true;
            }
        }
    }

    // Find Services associated with source-system
    public List<String> subscribedServicesOfTenantMaster(String tenantMaster){
        return tenantMstApisRepo.subscribedServicesOfTenantMaster(tenantMaster);
    }

    // Find source-system associated with Service
    public List<String> servicesSubscribedByTenantMasters(String serviceCode){
        return tenantMstApisRepo.servicesSubscribedByTenantMaster(serviceCode);
    }

    // Get total-sum of remaining & consumed Hits
    public Map<String, Object> getApisHitsSum(String tenantMaster){
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null) {
            return tenantMstApisRepo.getApisHitsSum(existingTenantMaster.getId());
        } else {
            // log.info("Source-System with the code: {}, does not exists!", tenantMaster);
            return null;
        }
    }

    // Get Apis Failed & Success Hits Sum
    public Map<String, Object> getApisFailedAndSuccessHitsSum(String tenantMaster){
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null) {
            return tenantMstApisRepo.getApisFailedAndSuccessHitsSum(existingTenantMaster.getId());
        } else {
            // log.info("Source-System with the code: {}, does not exists!", tenantMaster);
            return null;
        }
    }

    // Consume/Decrease Api-Hit Count
    public String consumeApi(String serviceCode, String tenantMaster) {

        // Fetching related entities
        ServiceIdentifier serviceIdentifier = serviceIdentifierService.findByCode(serviceCode);
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);

        // Checking if the user already has this API
        TenantMstApis existingTenantMstApis = tenantMstApisRepo.findByServiceIdentifierAndTenantMaster(serviceIdentifier, existingTenantMaster);

        if (existingTenantMstApis == null) {
            // log.info("You do not have subscription for this service, please get it first!");
            return "You do not have subscription for this service, please get it first!";
        } else {
            int remainingHitCounts = existingTenantMstApis.getRemainingHitsOfApi();
            if(remainingHitCounts == 0) {
                // log.info("You have consumed all your hits for this service, please get it first!");
                return "You have consumed all your hits for this service, please get it first!";
            } else {
                // Updating remaining hits if the entry already exists
                existingTenantMstApis.setRemainingHitsOfApi(existingTenantMstApis.getRemainingHitsOfApi() - 1);
                existingTenantMstApis.setConsumedHitsOfApi(existingTenantMstApis.getConsumedHitsOfApi() + 1);
                tenantMstApisRepo.save(existingTenantMstApis);
                // log.info("Remaining hits got updated!");
            }
        }
        return "Remaining hits got update for the service: "+serviceCode+", present in source-system: "+tenantMaster;
    }

}
