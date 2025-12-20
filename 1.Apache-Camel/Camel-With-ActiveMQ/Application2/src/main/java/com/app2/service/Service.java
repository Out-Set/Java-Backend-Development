package com.app2.service;

import com.app2.entity.UserDtl;
import com.app2.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repo repo;

    public UserDtl save(UserDtl userDtl){
        return repo.save(userDtl);
    }

    public List<UserDtl> getAll(){
        return repo.findAll();
    }
}
