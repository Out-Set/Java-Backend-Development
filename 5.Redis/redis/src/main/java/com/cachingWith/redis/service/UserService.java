package com.cachingWith.redis.service;

import com.cachingWith.redis.entity.Users;
import com.cachingWith.redis.repo.RedisRepo;
import com.cachingWith.redis.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    private RedisRepo redisRepo;

    @Autowired
    private UserRepo userRepo;

    private static final String REDIS_KEY = "USER";

    // Create User
    public Users createUser(Users users){
        return userRepo.save(users);
    }

    // Get by Id
    public Object getUser(Integer userId){
        Users users = (Users) redisRepo.read(REDIS_KEY, userId.toString());
        if(users != null){
            return users;
        } else {
            users = userRepo.findById(userId).orElse(null);
            if(users != null) {
                redisRepo.create(REDIS_KEY, userId.toString(), users);
                log.info("User details from Database ...");
                return users;
            }
        }
        return "User with this id does not exists";
    }

    // Get All
    public Map<Object, Object> getAll(){
        return redisRepo.readAll(REDIS_KEY);
    }

    // Delete
    public String deleteUser(Integer userId) {
        redisRepo.delete(REDIS_KEY, userId.toString());
        userRepo.deleteById(userId);
        return "User deleted successfully from database";
    }

    // Update
    public Object update(Users users){
        Users existingUser = userRepo.findById(users.getUserId()).orElse(null);
        if(existingUser != null){
            userRepo.save(users);
            return redisRepo.update(REDIS_KEY, users.getUserId().toString(), users);
        }
        return "User with this record not found";
    }
}
