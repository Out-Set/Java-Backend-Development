package com.example.batchProcessing.service;

import com.example.batchProcessing.entity.HelperEntity;
import com.example.batchProcessing.entity.ProcessedRecords;
import com.example.batchProcessing.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulingService {

    @Autowired
    private TestEntityService testEntityService;

    @Autowired
    private HelperEntityService helperEntityService;

    @Autowired
    private ProcessedRecordsService processedRecordsService;

    int rowNum = 0;
//    @Scheduled(cron = "0/10 * * * * *")
    public void insertRecordsIntoTestEntity(){
        List<TestEntity> testEntities = new ArrayList<>();
        for(int i=1; i<=100000; i++){
            TestEntity testEntity = new TestEntity();
            testEntity.setStatus("N");
            testEntity.setRowNumber(++rowNum);
            testEntity.setCreationTimeStamp(LocalDateTime.now());
            testEntities.add(testEntity);
        }
        testEntityService.insertRows(testEntities);
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void insertRecordsIntoHelperEntity() {

        List<ProcessedRecords> processedRecords = new ArrayList<>();
        List<HelperEntity> helperEntities = new ArrayList<>();
        List<TestEntity> testEntities = processedRecordsService.getTestEntityRows();
        System.out.println("testEntities size :: " + testEntities.size());

        for (TestEntity testEntity : testEntities) {
            // Prepare helper entity
            HelperEntity helperEntity = new HelperEntity();
            helperEntity.setRowNumber(testEntity.getRowNumber());
            helperEntity.setInsertionTimeStamp(testEntity.getCreationTimeStamp());
            helperEntities.add(helperEntity);

            // Prepare list of ProcessedRecords object
            ProcessedRecords processedRecord = new ProcessedRecords();
            processedRecord.setRecordId(testEntity.getId());
            processedRecord.setProcessedAt(LocalDateTime.now());
            processedRecords.add(processedRecord);
        }

        // Insert helper entities in batches
        batchInsertHelperEntities(helperEntities);

        // Insert processed records in batches
        batchInsertProcessedRecords(processedRecords);
    }

    private void batchInsertHelperEntities(List<HelperEntity> helperEntities) {
        int batchSize = 10000; // Adjust batch size based on performance testing
        for (int i = 0; i < helperEntities.size(); i += batchSize) {
            int end = Math.min(i + batchSize, helperEntities.size());
            List<HelperEntity> batch = helperEntities.subList(i, end);
            helperEntityService.insertRows(batch); // Ensure this method is optimized for batch inserts
            System.out.println("Inserted: " + batch.size() + " helperEntity rows in batch");
        }
    }

    private void batchInsertProcessedRecords(List<ProcessedRecords> processedRecords) {
        int batchSize = 10000; // Adjust batch size based on performance testing
        for (int i = 0; i < processedRecords.size(); i += batchSize) {
            int end = Math.min(i + batchSize, processedRecords.size());
            List<ProcessedRecords> batch = processedRecords.subList(i, end);
            processedRecordsService.insertRows(batch); // Ensure this method is optimized for batch inserts
            System.out.println("Inserted: " + batch.size() + " processedRecords rows in batch");
        }
    }



    /*
    private LocalDateTime schedulerLastRunTime;
    int rowNum = 0;

    // Insert Record into TestEntity in each 10-sec
    @Scheduled(cron = "0/10 * * * * *")
    public void insertRecordsIntoTestEntity(){
            List<TestEntity> testEntities = new ArrayList<>();
            for(int i=1; i<=1000000; i++){
                TestEntity testEntity = new TestEntity();
                testEntity.setStatus("N");
                testEntity.setRowNumber(++rowNum);
                testEntity.setCreationTimeStamp(LocalDateTime.now());
                testEntities.add(testEntity);
            }
            testEntityService.insertRows(testEntities);
    }

    // Insert Record into TestEntity in each 15-sec
    @Scheduled(cron = "0/10 * * * * *")
    public void insertRecordsIntoHelperEntity(){

        LocalDateTime currentRunTime = LocalDateTime.now();
        if (schedulerLastRunTime == null) {
            schedulerLastRunTime = LocalDateTime.now();
        }

        List<HelperEntity> helperEntities = new ArrayList<>();
        List<TestEntity> testEntities = testEntityService.getTestEntityRows(schedulerLastRunTime);
        System.out.println("testEntities size :: "+testEntities.size());
        for (TestEntity testEntity : testEntities) {
            // Prepare helper entity
            HelperEntity helperEntity = new HelperEntity();
            helperEntity.setRowNumber(testEntity.getRowNumber());
            helperEntity.setInsertionTimeStamp(testEntity.getCreationTimeStamp());
            helperEntities.add(helperEntity);
        }
        helperEntityService.insertRows(helperEntities);
        System.out.println("Inserted: "+helperEntities.size()+" helperEntity rows");

        // Update on each execution
        schedulerLastRunTime = currentRunTime;
        System.out.println("schedulerLastRunTime :: "+schedulerLastRunTime);
    }
    */


    //--------------------------------Status based: Working Fine---------------------------------//
    /*
    private LocalDateTime schedulerLastRunTime;
    int rowNum = 0;

    // Insert Record into TestEntity in each 10-sec
    @Scheduled(cron = "0/10 * * * * *")
    public void insertRecordsIntoTestEntity(){
            List<TestEntity> testEntities = new ArrayList<>();
            for(int i=1; i<=1000; i++){
                TestEntity testEntity = new TestEntity();
                testEntity.setStatus("N");
                testEntity.setRowNumber(++rowNum);
                testEntity.setCreationTimeStamp(LocalDateTime.now());
                testEntities.add(testEntity);
            }
            testEntityService.insertRows(testEntities);
    }

    // Insert Record into TestEntity in each 15-sec
    @Scheduled(cron = "0/15 * * * * *")
    public void insertRecordsIntoHelperEntity(){
        List<HelperEntity> helperEntities = new ArrayList<>();
        List<TestEntity> testEntitiesToMarkP = new ArrayList<>();
        List<TestEntity> testEntities = testEntityService.getUnProcessedRows("N");
        System.out.println("testEntities size :: "+testEntities.size());
        for (TestEntity testEntity : testEntities) {
            // Prepare helper entity
            HelperEntity helperEntity = new HelperEntity();
            helperEntity.setRowNumber(testEntity.getRowNumber());
            helperEntity.setInsertionTimeStamp(testEntity.getCreationTimeStamp());
            helperEntities.add(helperEntity);

            // Mark Test-Entities Processed
            testEntity.setStatus("P");
            testEntitiesToMarkP.add(testEntity);
        }
        helperEntityService.insertRows(helperEntities);
        System.out.println("Inserted: "+helperEntities.size()+" helperEntity rows");

        // Mark Test-Entities Processed
        testEntityService.insertRows(testEntitiesToMarkP);
    }
    */
}
