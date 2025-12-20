package com.example.batchProcessing.repo;

import com.example.batchProcessing.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestEntityRepo extends JpaRepository<TestEntity, Long> {

    String qry1 = """
                    select t from TestEntity t 
                    where t.creationTimeStamp < :schedulerLastRunTime
                """;
    @Query(value = qry1)
    List<TestEntity> getTestEntityRows(@Param("schedulerLastRunTime") LocalDateTime schedulerLastRunTime);


    String qry2 = """
                    select t from TestEntity t 
                    where t.status = :status
                """;
    @Query(value = qry2)
    List<TestEntity> getUnProcessedRows(@Param("status") String status);

}
