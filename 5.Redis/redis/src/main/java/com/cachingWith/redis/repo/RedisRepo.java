package com.cachingWith.redis.repo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class RedisRepo {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Clear all Redis databases, during application start
    @PostConstruct
    public void clearCache() {
        RedisConnection connection = redisTemplate.getRequiredConnectionFactory().getConnection();
        // Execute the FLUSHALL command directly
        log.info("Clearing redis cache ...");
        connection.execute("FLUSHALL");
        connection.close();
        log.info("Redis cache is cleared ...");
    }

    // Clear the current Redis database
    public void clearDbCache() {
        RedisConnection connection = redisTemplate.getRequiredConnectionFactory().getConnection();
        connection.execute("FLUSHDB");
        connection.close();
    }

    // Save
    public void create(String KEY, String objectKey, Object object){
        redisTemplate.opsForHash().put(KEY, objectKey, object);
        log.info("Data with Key: {}, with value: {}, cached into redis", KEY, object);
    }

    // Get by Id
    public Object read(String KEY, String objectKey){
        Object data = redisTemplate.opsForHash().get(KEY, objectKey);
        log.info("Data from redis for key: {}, and id: {}, the value is: {}", KEY, objectKey, data);
        return data;
    }

    // Get All
    public Map<Object, Object> readAll(String KEY){
        Map<Object, Object> allData = redisTemplate.opsForHash().entries(KEY);
        log.info("Retrieved all data from redis for the key: {}, values: {}", KEY, allData);
        return allData;
    }

    // Delete
    public void delete(String KEY, String objectKey){
        log.info("Data deleted from redis for key: {}, and id: {}", KEY, objectKey);
        redisTemplate.opsForHash().delete(KEY, objectKey);
    }

    // Update
    public Object update(String KEY, String objectKey, Object object){
        log.info("Data updated into redis for key: {}, and id: {}", KEY, object);
        redisTemplate.opsForHash().put(KEY, objectKey, object);
        return object;
    }
}
