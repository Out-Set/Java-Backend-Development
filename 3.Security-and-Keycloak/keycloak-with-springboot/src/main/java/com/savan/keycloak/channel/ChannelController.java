package com.savan.keycloak.channel;

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
@RequestMapping("/api/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    // Create Channel
    @PostMapping("/create/tenant-master/{tenantMaster}")
    public ResponseEntity<String> createChannel(@RequestBody Map<String, String> channel, @PathVariable String tenantMaster) {
        try {
            Channel newChannel = new Channel();
            newChannel.setFirstName(channel.get("firstName"));
            newChannel.setLastName(channel.get("lastName"));
            newChannel.setUsername(channel.get("username"));
            newChannel.setEmail(channel.get("email"));
            newChannel.setIsEmailVerified(channel.get("isEmailVerified"));
            newChannel.setPassword(channel.get("password"));
            newChannel.setScope(channel.get("scope"));

            String resp = channelService.createChannel(newChannel, tenantMaster);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get All Channels
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllChannels() {
        try {
            List<Map<String, Object>> channels = channelService.getAllChannels();
            return ResponseEntity.ok(channels);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get by Channel-Id
    @GetMapping("/channel-id/{channelId}")
    public ResponseEntity<Map<String, Object>> readChannelById(@PathVariable Long channelId) {
        try {
            Map<String, Object> channels = channelService.readChannelById(channelId);
            return ResponseEntity.ok(channels);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Channel by Channel-Name
    @GetMapping("/channel-name/{channelName}")
    public ResponseEntity<List<Map<String, Object>>> getChannelByChannelName(@PathVariable String channelName) {
        try {
            List<Map<String, Object>> user = channelService.getChannelByChannelName(channelName);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Read Channel by Tenant-Master
    @GetMapping("/tenant-master/{tenantMaster}")
    public ResponseEntity<List<Map<String, Object>>> readChannelByTenantMaster(@PathVariable String tenantMaster) {
        try {
            List<Map<String, Object>> user = channelService.readChannelByTenantMaster(tenantMaster);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Channel by Channel-Name and Tenant-Master
    @GetMapping("/tenant-master/{tenantMaster}/channel-name/{channelName}")
    public ResponseEntity<Map<String, Object>> getChannelByChannelNameAndTenantMaster(@PathVariable String channelName, @PathVariable String tenantMaster) {
        try {
            Map<String, Object> user = channelService.getChannelByChannelNameAndTenantMaster(channelName, tenantMaster);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update Channel
    @PutMapping
    public ResponseEntity<String> updateChannelDetails(@RequestBody Map<String, String> channel) {
        try {
            String resp = channelService.updateChannel(channel);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update user details: " + e.getMessage());
        }
    }

    // Delete Channel
    @DeleteMapping("/tenant-master/{tenantMaster}/channel-name/{channelName}")
    public ResponseEntity<String> deleteUser(@PathVariable String tenantMaster, @PathVariable String channelName) {
        try {
            String resp = channelService.deleteChannel(tenantMaster, channelName);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }
}


