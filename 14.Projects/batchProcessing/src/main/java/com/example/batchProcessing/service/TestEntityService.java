package com.example.batchProcessing.service;

import com.example.batchProcessing.entity.TestEntity;
import com.example.batchProcessing.repo.TestEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TestEntityService {

    @Autowired
    private TestEntityRepo testEntityRepo;

    @Transactional
    public void insertRow(TestEntity testEntity){
        testEntityRepo.save(testEntity);
    }

    public List<TestEntity> getTestEntityRows(LocalDateTime schedulerLastRunTime){
        return testEntityRepo.getTestEntityRows(schedulerLastRunTime);
    }

    public List<TestEntity> getUnProcessedRows(String status){
        return testEntityRepo.getUnProcessedRows(status);
    }

    // Must keep @Transactional
    @Transactional
    public void insertRows(List<TestEntity> testEntities){
        testEntityRepo.saveAll(testEntities);
    }

    public List<TestEntity> getRows(){
         return testEntityRepo.findAll();
    }

    // For async service
    @Async
    public CompletableFuture<Void> insertRowsAsync(List<TestEntity> testEntities) {
        testEntityRepo.saveAll(testEntities);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<TestEntity>> getRowsAsync() {
        List<TestEntity> rows = testEntityRepo.findAll();
        return CompletableFuture.completedFuture(rows);
    }
}
