package com.savan.keycloak.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepo extends JpaRepository<Channel, Long> {

    // Find by userId
    @Query(value = "select * from channel where id=:channelId", nativeQuery = true)
    Channel findByChannelId(Long channelId);

    // Find by username
    @Query(value = "select * from channel where username=:channelName", nativeQuery = true)
    List<Channel> findByChannelName(String channelName);

    // Find By channelName and Tenant-Master
    @Query(value = "SELECT * FROM channel WHERE username = :channelName AND channel.tenant_mst_id = :tenantMasterId", nativeQuery = true)
    Channel findByChannelNameAndTenantMaster(String channelName, Long tenantMasterId);

}
