package com.savan.redis.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.savan.redis.entity.Users;
import com.savan.redis.repo.RedisClientRepo;
import com.savan.redis.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private RedisClientRepo redisClientRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JsonUtil jsonUtil;

    // Create User
    public Users createUser(Users users){
        return userRepo.save(users);
    }

    // Get by Id
    public Object getUser(Integer userId){
        // Users users = redisClientRepo.getData("users_"+userId, new TypeReference<Users>(){});
        Users users = redisClientRepo.getData("users_"+userId, Users.class);
        if(users != null){
            // log.info("User details from Redis-Cache ...");
            System.out.println("User details from Redis-Cache ...");
            return users;
        } else {
            users = userRepo.findById(userId).orElse(null);
            if(users != null) {
                redisClientRepo.saveData("users_"+userId, users);
                // log.info("User details from Database ...");
                System.out.println("User details from Database ...");
                return users;
            }
        }
        return "User with this id does not exists";
    }

    // Get All
    public List<Users> getAll(){
        List<Users> users = (List<Users>) redisClientRepo.getData("users", new TypeReference<List<Users>>(){});
        if(users != null){
            // log.info("Users details from Redis-Cache ...");
            System.out.println("Users details from Redis-Cache ...");
            return users;
        } else {
            users = userRepo.findAll();
            redisClientRepo.saveData("users", users);
            // log.info("Users details from Database ...");
            System.out.println("Users details from Database ...");
            return users;
        }
    }

    // Delete
    public String deleteUser(Integer userId) {
        redisClientRepo.deleteData("users_"+userId);
        userRepo.deleteById(userId);
        return "User deleted successfully from database";
    }

    // Update
    public Object update(Users users){
        Users existingUser = userRepo.findById(users.getUserId()).orElse(null);
        if(existingUser != null){
            userRepo.save(users);
            redisClientRepo.saveData("users_"+users.getUserId(), users);
            return "User records updated successfully!";
        } else {
            return "User with this record not found!";
        }
    }
}
