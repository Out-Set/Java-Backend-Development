package com.example.batchProcessing.service;

import com.example.batchProcessing.entity.HelperEntity;
import com.example.batchProcessing.repo.HelperEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HelperEntityService {

    @Autowired
    private HelperEntityRepo helperEntityRepo;

    @Transactional
    public void insertRows(List<HelperEntity> helperEntities){
        helperEntityRepo.saveAll(helperEntities);
    }

    pu
}
