package com.example.demo.sqlDataToJson.batchProcessingWithThreadPool;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//@Component
public class ExecuteTask {

    @Autowired
    private AsyncQueryService asyncQueryService;

//    @PostConstruct
    public void execute(){
        int batchSize = 200;
        String nativeQuery = "select * from service_req_res_audit_dtl";
        List<Object[]> data = asyncQueryService.executeNativeQueryAsync(nativeQuery, batchSize);
        System.out.println("Data length :: "+data.size());
        System.out.println("Data :: "+data);
    }
}
