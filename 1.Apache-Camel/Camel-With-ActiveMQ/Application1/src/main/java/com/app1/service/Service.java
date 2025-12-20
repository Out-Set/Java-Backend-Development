package com.app1.service;

import com.app1.entity.UserFullDtl;
import com.app1.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repo repo;

    public UserFullDtl save(UserFullDtl userDtl){
        return repo.save(userDtl);
    }

    public List<UserFullDtl> saveAll(List<UserFullDtl> userDtl){
        return repo.saveAll(userDtl);
    }

    public List<UserFullDtl> getAll(){
        return repo.findAll();
    }
}
