package com.savan.keycloak.emRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EntityManagerRepo {

    @Autowired
    private EntityManager entityManager;

    // --------------------- Users Entity related --------------------- //

    // Get All Channels
    public List<Map<String, Object>> getAllChannels() {
        String query = """
                SELECT
                    u.id AS id,
                    u.first_name as firstName,
                    u.last_name as lastName,
                    u.username as channelName,
                    u.email as email,
                    u.is_email_verified as isEmailVerified,
                    u.scope AS scope,
                    tm.code AS tenantMaster
                FROM
                    channel u
                LEFT JOIN
                    tenant_mst tm ON u.tenant_mst_id = tm.id
                ORDER BY
                    u.id
                """;
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.unwrap(org.hibernate.query.Query.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Channel by Id
    public Map<String, Object> readChannelById(Long channelId) {
        String query = """
                SELECT
                    u.id AS id,
                    u.first_name as firstName,
                    u.last_name as lastName,
                    u.username as channelName,
                    u.email as email,
                    u.is_email_verified as isEmailVerified,
                    u.scope AS scope,
                    tm.code AS tenantMaster
                FROM
                    channel u
                LEFT JOIN
                    tenant_mst tm ON u.tenant_mst_id = tm.id
                WHERE
                    u.id = :channelId
                """;
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("channelId", channelId);
        queryObject.unwrap(org.hibernate.query.Query.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = queryObject.getResultList();

        // If there's at least one result, return the first map, otherwise return an empty map
        return result.isEmpty() ? new HashMap<>() : result.get(0);
    }

    // Get Channel by Channel-Name
    public List<Map<String, Object>> getChannelByChannelName(String channelName) {
        String query = """
				SELECT
				    u.id AS id,
				    u.first_name as firstName,
				    u.last_name as lastName,
				    u.username as channelName,
				    u.email as email,
				    u.is_email_verified as isEmailVerified,
				    u.scope AS scope,
				    tm.code AS tenantMaster
				FROM
				    channel u
				LEFT JOIN
				    tenant_mst tm ON u.tenant_mst_id = tm.id
				WHERE
				    u.username = :channelName 
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("channelName", channelName);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Channel by Tenant-Master
    public List<Map<String, Object>> readChannelByTenantMaster(String tenantMaster) {
        String query = """
				SELECT
				    u.id AS id,
				    u.first_name as firstName,
				    u.last_name as lastName,
				    u.username as channelName,
				    u.email as email,
				    u.is_email_verified as isEmailVerified,
				    u.scope AS scope,
				    tm.code AS tenantMaster
				FROM
				    channel u
				LEFT JOIN
				    tenant_mst tm ON u.tenant_mst_id = tm.id
				WHERE
				    tm.code = :tenantMaster
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("tenantMaster", tenantMaster);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Channel by Channel-Name and Tenant-Master
    public Map<String, Object> getChannelByChannelNameAndTenantMaster(String channelName, String tenantMaster) {
        String query = """
				SELECT
				    u.id AS id,
				    u.first_name as firstName,
				    u.last_name as lastName,
				    u.username as channelName,
				    u.email as email,
				    u.is_email_verified as isEmailVerified,
				    u.scope AS scope,
				    tm.code AS tenantMaster
				FROM
				    channel u
				LEFT JOIN
				    tenant_mst tm ON u.tenant_mst_id = tm.id
				WHERE
				    u.username = :channelName AND tm.code = :tenantMaster 
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("channelName", channelName);
        queryObject.setParameter("tenantMaster", tenantMaster);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = queryObject.getResultList();

        // If there's at least one result, return the first map, otherwise return an empty map
        return result.isEmpty() ? new HashMap<>() : result.get(0);
    }


    // --------------------- Source-System-Apis Entity related --------------------- //

    // Get all Tenant-Master-Apis
    public List<Map<String, Object>> getAllTenantMasterApis() {
        String query = """
				SELECT
				    tma.id AS id,
				    tma.remaining_hits_of_api AS remainingHitsOfApi,
				    tma.consumed_hits_of_api AS consumedHitsOfApi,
				    si.name AS serviceCode,
				    c.username AS channelName,
				    tm.name AS tenantMaster
				FROM
				    tenant_mst_apis tma
				INNER JOIN
				    service_identifier_mst si ON tma.service_id = si.id
				INNER JOIN
				    tenant_mst tm ON tma.tenant_mst_id = tm.id
				INNER JOIN
				    channel c ON tma.tenant_mst_id = c.id
				ORDER BY
				    tma.id
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Tenant-Master-Apis Details with Tenant-Master
    public List<Map<String, Object>> findByTenantMaster(String tenantMaster) {
        String query = """
				SELECT
				    tma.id AS id,
				    tma.remaining_hits_of_api AS remainingHitsOfApi,
				    tma.consumed_hits_of_api AS consumedHitsOfApi,
				    si.name AS serviceCode,
				    c.username AS channelName,
				    tm.name AS tenantMaster
				FROM
				    tenant_mst_apis tma
				INNER JOIN
				    service_identifier_mst si ON tma.service_id = si.id
				INNER JOIN
				    tenant_mst tm ON tma.tenant_mst_id = tm.id
				INNER JOIN
				    channel c ON tma.tenant_mst_id = c.id
				WHERE
					tm.code = :tenantMaster
				ORDER BY
				    tma.id
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("tenantMaster", tenantMaster);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Tenant-Master-Apis Details with Service-Code
    public List<Map<String, Object>> findByServiceCode(String serviceCode) {
        String query = """
				SELECT
				    tma.id AS id,
				    tma.remaining_hits_of_api AS remainingHitsOfApi,
				    tma.consumed_hits_of_api AS consumedHitsOfApi,
				    si.name AS serviceCode,
				    c.username AS channelName,
				    tm.name AS tenantMaster
				FROM
				    tenant_mst_apis tma
				INNER JOIN
				    service_identifier_mst si ON tma.service_id = si.id
				INNER JOIN
				    tenant_mst tm ON tma.tenant_mst_id = tm.id
				INNER JOIN
				    channel c ON tma.tenant_mst_id = c.id
				WHERE
					si.code = :serviceCode
				ORDER BY
				    tma.id
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("serviceCode", serviceCode);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return queryObject.getResultList();
    }

    // Get Tenant-Master-Apis with Service-Code and Tenant-Master
    public Map<String, Object> findByServiceCodeAndTenantMaster(String tenantMaster, String serviceCode) {
        String query = """
				SELECT
				    tma.id AS id,
				    tma.remaining_hits_of_api AS remainingHitsOfApi,
				    tma.consumed_hits_of_api AS consumedHitsOfApi,
				    si.name AS serviceCode,
				    c.username AS channelName,
				    tm.name AS tenantMaster
				FROM
				    tenant_mst_apis tma
				INNER JOIN
				    service_identifier_mst si ON tma.service_id = si.id
				INNER JOIN
				    tenant_mst tm ON tma.tenant_mst_id = tm.id
				INNER JOIN
				    channel c ON tma.tenant_mst_id = c.id
				WHERE
					tm.code = :tenantMaster AND si.code = :serviceCode
				""";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("serviceCode", serviceCode);
        queryObject.setParameter("tenantMaster", tenantMaster);
        queryObject.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> result = queryObject.getResultList();

        // If there's at least one result, return the first map, otherwise return an empty map
        return result.isEmpty() ? new HashMap<>() : result.get(0);
    }
}
