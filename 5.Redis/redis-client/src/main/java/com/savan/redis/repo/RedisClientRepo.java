package com.savan.redis.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.savan.redis.service.JsonUtil;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisClientRepo {

    @Autowired
    private JsonUtil jsonUtil;

    private final RedisCommands<String, String> redisCommands;

    public RedisClientRepo(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
    }

    public <T> void saveData(String key, T object) {
        String value = jsonUtil.convertObjectToJson(object);
        redisCommands.set(key, value);
    }

    // For Single-JSON only
    public <T> T getData(String key, Class<T> clazz) {
        String value = redisCommands.get(key);
        return jsonUtil.convertJsonToObject(value, clazz);
    }

    // To get both JSON-Array/JSON-String to Object
    public <T> T getData(String key, TypeReference<T> typeReference) {
        String value = redisCommands.get(key);
        return jsonUtil.convertJsonToObject(value, typeReference);
    }

    public void deleteData(String key) {
        redisCommands.del(key);
    }
}
