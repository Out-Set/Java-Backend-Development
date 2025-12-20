package com.example.batchProcessing.service;

import com.example.batchProcessing.entity.ProcessedRecords;
import com.example.batchProcessing.entity.TestEntity;
import com.example.batchProcessing.repo.ProcessedRecordsRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProcessedRecordsService {

    @Autowired
    private ProcessedRecordsRepo processedRecordsRepo;

    @Autowired
    private EntityManager entityManager;

    // Select new Records from Test-Entity
    public List<TestEntity> getTestEntityRows() {
        String hqlQuery = """
                    SELECT t FROM TestEntity t
                    WHERE NOT EXISTS (
                         SELECT 1 FROM ProcessedRecords pr
                         WHERE pr.recordId = t.id
                    )
                """;
        try {
            Query query = entityManager.createQuery(hqlQuery, TestEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            // Handle or log exception as needed
            e.printStackTrace();
            return List.of(); // Return an empty list or handle accordingly
        }
    }

    @Transactional
    public void insertRows(List<ProcessedRecords> processedRecords){
        processedRecordsRepo.saveAll(processedRecords);
    }
}
