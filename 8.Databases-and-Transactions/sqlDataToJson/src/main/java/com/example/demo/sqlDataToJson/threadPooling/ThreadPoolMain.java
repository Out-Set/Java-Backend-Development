package com.example.demo.sqlDataToJson.threadPooling;

import com.example.demo.sqlDataToJson.batchProcessingWithThreadPool.AsyncQueryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ThreadPoolMain {



    @Autowired
    private AsyncQueryService asyncQueryService;

//    @PostConstruct
    public void execute() throws Exception {

        int noOfThreads = 5;
        int maxNoOfTasks = 10;
        ThreadPool threadPool = new ThreadPool(noOfThreads, maxNoOfTasks);

        for(int i=0; i<maxNoOfTasks; i++) {

            int taskNo = i;
            threadPool.execute( () -> {
                String message = Thread.currentThread().getName() + ": Task " + taskNo ;
                System.out.println(message);
                int batchSize = 200;
                String nativeQuery = "select * from service_req_res_audit_dtl";
                List<Object[]> data = asyncQueryService.executeNativeQueryAsync(nativeQuery, batchSize);
                System.out.println("Data length :: "+data.size());
                System.out.println("Data :: "+data);
            });
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();
    }
}
