package com.savan.keycloak.channel;

import com.savan.keycloak.emRepository.EntityManagerRepo;
import com.savan.keycloak.tenantMaster.TenantMaster;
import com.savan.keycloak.tenantMaster.TenantMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChannelService {

    @Autowired
    private TenantMasterService tenantMasterService;

    @Autowired
    private ChannelRepo channelRepo;

    @Autowired
    private EntityManagerRepo entityManagerRepo;

    // Create Channel
    @Transactional
    public String createChannel(Channel channel, String tenantMaster) {

        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null) {
            Channel existingUser = channelRepo.findByChannelNameAndTenantMaster(channel.getUsername(), existingTenantMaster.getId());
            if(existingUser != null) {
                // log.info("User already exists with username - {}, in the source-system - {}", channel.getUsername(), tenantMaster);
                return "User already exists with username - " + channel.getUsername() + ", in the source-system - "+tenantMaster;
            } else {
                channel.setTenantMaster(existingTenantMaster);
                // channel.setPassword(passwordEncoder.encode(user.getPassword()));
                channelRepo.save(channel);
                // log.info("User created successfully!");
                return "User created successfully!";
            }

        } else {
            // log.info("Source system with id: {}, does not exits!", tenantMaster);
            return "Source system with id: " + tenantMaster + ", does not exits!";
        }

    }

    // Get All Channels
    public List<Map<String, Object>> getAllChannels() {
        return entityManagerRepo.getAllChannels();
    }

    // Get By Channel-Id
    public Map<String, Object> readChannelById(Long channelId) {
        return entityManagerRepo.readChannelById(channelId);
    }

    // Get Channel by Channel-Name
    public List<Map<String, Object>> getChannelByChannelName(String channelName) {
        return entityManagerRepo.getChannelByChannelName(channelName);
    }

    // Read Channel by Tenant-Master
    public List<Map<String, Object>> readChannelByTenantMaster(String tenantMaster) {
        return entityManagerRepo.readChannelByTenantMaster(tenantMaster);
    }

    // Get Channel by Channel-Name and Tenant-Master
    public Map<String, Object> getChannelByChannelNameAndTenantMaster(String channelName, String tenantMaster) {
        return entityManagerRepo.getChannelByChannelNameAndTenantMaster(channelName, tenantMaster);
    }

    // Find user by Channel-Name and SourceSystem with jpaRepo
    public Channel findByChannelNameAndTenantMaster(String channelName, String tenantMaster) {
        return channelRepo.findByChannelNameAndTenantMaster(channelName, tenantMasterService.findByCode(tenantMaster).getId());
    }

    // *Most-Imp: Find By Channel-Name and TenantMasterId
    public Channel findByChannelNameAndTenantMasterId(String channelName, Long tenantMasterId) {
        return channelRepo.findByChannelNameAndTenantMaster(channelName, tenantMasterId);
    }

    // Update Channel details
    public String updateChannel(Map<String, String> channel) {

        Channel existingChannel = channelRepo.findByChannelNameAndTenantMaster(channel.get("username"), tenantMasterService.findByCode(channel.get("tenantMaster")).getId());
        if (existingChannel != null) {
            existingChannel.setFirstName(channel.get("firstName"));
            existingChannel.setLastName(channel.get("lastName"));
            existingChannel.setEmail(channel.get("email"));
            existingChannel.setIsEmailVerified(channel.get("isEmailVerified"));
            // existingUser.setPassword(passwordEncoder.encode(user.get("password")));
            channelRepo.save(existingChannel);

            // log.info("User details with username {}, updated successfully!", channel.get("username"));
            return "User details with username " + channel.get("username") +", updated successfully!";
        } else {
            // log.info("User with username {}, does not exists!", channel.get("username"));
            return "User with username " + channel.get("username") + ", does not exists!";
        }
    }

    // Delete user by User-Name
    public String deleteChannel(String tenantMaster, String channelName) {
        TenantMaster existingTenantMaster = tenantMasterService.findByCode(tenantMaster);
        if(existingTenantMaster != null) {
            Channel existingUser = channelRepo.findByChannelNameAndTenantMaster(channelName, existingTenantMaster.getId());
            if (existingUser != null) {
                channelRepo.delete(existingUser);
                // log.info("User {}, deleted successfully!", channelName);
                return channelName+" deleted successfully!";
            } else {
                // log.info("User {} does not exists!", channelName);
                return "User " + channelName + " does not exists!";
            }
        } else {
            // log.info("User {} does not exists in source-system {}", channelName, tenantMaster);
            return "User " + channelName + " does not exists, in source-system " + tenantMaster;
        }
    }
}