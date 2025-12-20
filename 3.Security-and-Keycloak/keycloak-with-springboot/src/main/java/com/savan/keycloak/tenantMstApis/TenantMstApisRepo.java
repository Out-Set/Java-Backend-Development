package com.savan.keycloak.tenantMstApis;

import com.savan.keycloak.channel.Channel;
import com.savan.keycloak.serviceIdentifier.ServiceIdentifier;
import com.savan.keycloak.tenantMaster.TenantMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TenantMstApisRepo extends JpaRepository<TenantMstApis, Long> {

    // Find by ServiceIdentifier and SourceSystemIdentifier
    TenantMstApis findByServiceIdentifierAndTenantMaster(ServiceIdentifier serviceIdentifier, TenantMaster tenantMaster);

    // Find by ServiceIdentifier and SourceSystemIdentifier
    TenantMstApis findByServiceIdentifierAndTenantMasterAndChannel(ServiceIdentifier serviceIdentifier, TenantMaster tenantMaster, Channel channel);

    // Find Services associated with source-system
    @Query("SELECT si.code FROM TenantMstApis sa JOIN sa.serviceIdentifier si JOIN sa.tenantMaster ssi WHERE ssi.code = :tenantMaster")
    List<String> subscribedServicesOfTenantMaster(String tenantMaster);

    // Find Services associated with source-system
    @Query("SELECT ssi.code FROM TenantMstApis sa JOIN sa.serviceIdentifier si JOIN sa.tenantMaster ssi WHERE si.code = :serviceCode")
    List<String> servicesSubscribedByTenantMaster(String serviceCode);

    // Get sum of remaining & consumed Hits
    String apisHitsSum = """
            SELECT 
                SUM(remaining_hits_of_api) as remainingHits, 
                SUM(consumed_hits_of_api) as consumedHits 
            FROM tenant_mst_apis 
            WHERE tenant_mst_id = :tenantMasterId
            """;
    @Query(value = apisHitsSum, nativeQuery = true)
    Map<String, Object> getApisHitsSum(Long tenantMasterId);

    // Get Apis Failed & Success Hits Sum
    String apisFailedAndSuccessHitsSum = """
			SELECT 
				SUM(CASE WHEN status = 1 THEN status ELSE 0 END) AS failedHits, 
			    SUM(CASE WHEN status = 2 THEN status ELSE 0 END) AS successHits 
			FROM service_req_res_audit_dtl
			WHERE source_system_identifier = :tenantMasterId
            """;
    @Query(value = apisFailedAndSuccessHitsSum, nativeQuery = true)
    Map<String, Object> getApisFailedAndSuccessHitsSum(Long tenantMasterId);

}
