package com.savan.keycloak.channelApis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelApisRepo extends JpaRepository<ChannelApis, Long> {

    // Find by Service-Identifier, Tenant-Master and Channel
    @Query("SELECT ca FROM ChannelApis ca WHERE ca.channel.id = :channelId AND ca.serviceIdentifier.id = :serviceId AND ca.tenantMaster.id = :tenantMasterId")
    ChannelApis findByServiceIdentifierAndTenantMasterIdAndChannel(Long channelId, Long serviceId, Long tenantMasterId);

    // Find Services assigned to User w.r.t. Source-System
    @Query("SELECT si.code FROM ChannelApis ua JOIN ua.serviceIdentifier si WHERE ua.channel.id  = :channelId AND ua.tenantMaster.id = :tenantMasterId")
    List<String> servicesAssignedToChannel(Long channelId, Long tenantMasterId);

    // Find Users Associated with Service w.r.t. Source-System
    @Query("SELECT u.username FROM Channel u JOIN u.channelApis ua WHERE ua.serviceIdentifier.id = :serviceId AND ua.tenantMaster.id = :tenantMasterId")
    List<String> channelsAssociatedWithService(Long serviceId, Long tenantMasterId);

    // Find by serviceIdentifier, user and sourceSystemIdentifier
    @Query("FROM ChannelApis u where u.serviceIdentifier.code = :serviceCode and u.tenantMaster.code = :tenantMaster and u.channel.username = :channelName")
    ChannelApis findByServiceIdentifierAndTenantMasterAndChannel(String serviceCode, String tenantMaster, String channelName);

}
