package com.example.demo.sqlDataToJson.batchProcessingWithThreadPool;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipFile;

@Service
public class AsyncQueryService {

    @Autowired
    private EntityManager entityManager;

    @Async
    public List<Object[]> executeNativeQueryAsync(String nativeQuery, int batchSize) {

        int i=0;
        List<Object[]> finalResults = new ArrayList<>();
        int offset = 0;
        boolean moreResults = true;

        while (moreResults) {
            Query query = entityManager.createNativeQuery(nativeQuery);
            query.setMaxResults(batchSize);
            query.setFirstResult(offset);
            List<Object[]> batchResults = query.getResultList();

            System.out.println(i+++" :: "+batchResults.size());
            for(Object[] objects: batchResults){
                System.out.println(objects[0]);
            }
            finalResults.addAll(batchResults);

            if (batchResults.size() < batchSize) {
                moreResults = false;
            } else {
                offset += batchSize;
            }
        }

        return finalResults;
    }


/*
    public List<Object[]> executeNativeQueryAsync(String nativeQuery, int batchSize) {
        int i=0;
        int offset = 0;
        boolean moreResults = true;
        List<Thread> threads = new ArrayList<>();
        List<List<Object[]>> results = new ArrayList<>();

        while (moreResults) {

            int currentOffset = offset;
            List<Object[]> batchResults = new ArrayList<>();

            Thread thread = new Thread(() -> {
                Query query = entityManager.createNativeQuery(nativeQuery);
                query.setMaxResults(batchSize);
                query.setFirstResult(currentOffset);
                batchResults.addAll(query.getResultList());
                System.out.println(i + " :: " + batchResults.size());
                System.out.println(i + " :: thread");
                synchronized (results) {
                    results.add(batchResults);
                }
            });
            threads.add(thread);
            thread.start();
            offset += batchSize;
            moreResults = batchResults.size() == batchSize;
        }


        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Combine results
        List<Object[]> finalResults = new ArrayList<>();
        for (List<Object[]> batchResult : results) {
            finalResults.addAll(batchResult);
        }

        return finalResults;
    }
*/

}
