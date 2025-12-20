package com.example.batchProcessing.repo;

import com.example.batchProcessing.entity.ProcessedRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedRecordsRepo extends JpaRepository<ProcessedRecords, Long> {

}
