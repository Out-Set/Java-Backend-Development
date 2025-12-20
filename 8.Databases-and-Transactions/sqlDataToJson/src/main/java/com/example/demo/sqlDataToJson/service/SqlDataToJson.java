package com.example.demo.sqlDataToJson.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SqlDataToJson {

    @Autowired
    private EntityManager entityManager;

    @Async
    public void executeNativeQueryAsync(String nativeQuery, int batchSize) {

        int offset = 0;
        boolean moreResults = true;

        while (moreResults) {
            Query query = entityManager.createNativeQuery(nativeQuery);
            query.setMaxResults(batchSize);
            query.setFirstResult(offset);

            query.unwrap(org.hibernate.query.NativeQuery.class)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List<?> batchResults = query.getResultList();

            // Prepare Json List
            prepareJson(batchResults);

            if (batchResults.size() < batchSize) {
                moreResults = false;
            } else {
                offset += batchSize;
            }
        }
    }

    // Prepare Json list
    public void prepareJson(List<?> resultList){

        // Convert each data in list into json object
        List<String> jsonStringList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object result : resultList) {
            // Convert each row to a JSON object
            Map<String, Object> row = (Map<String, Object>) result;
            String jsonResult;
            try {
                jsonResult = objectMapper.writeValueAsString(row);
                jsonStringList.add(jsonResult);
                System.out.println(jsonResult);
            } catch (Exception e) {
                System.out.println("An Exception Occurred!");
            }
        }

        System.out.println("jsonStringList :: "+jsonStringList);
    }



    // Other task IMP
    public void fetchData(){

        String nativeQuery = """
                SELECT id as eid, pan_number as panNum
                FROM experian_data
            """;
        Query query = entityManager.createNativeQuery(nativeQuery);

        query.unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<?> resultList = query.getResultList();

        // Convert each data in list into json object
        List<String> jsonStringList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object result : resultList) {
            // Convert each row to a JSON object
            Map<String, Object> row = (Map<String, Object>) result;
            String jsonResult;
            try {
                jsonResult = objectMapper.writeValueAsString(row);
                jsonStringList.add(jsonResult);
                System.out.println(jsonResult);
            } catch (Exception e) {
                System.out.println("An Exception Occurred!");
            }
        }

        System.out.println("jsonStringList :: "+jsonStringList);

        // Convert whole List into json string
        /*
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult;
        try {
            jsonResult = objectMapper.writeValueAsString(resultList);
            System.out.println(jsonResult);
        } catch (Exception e) {
            System.out.println("An Exception Occurred!");
        }
         */
    }
}

/*
resp:
{"pannum":"SCTPK7678T","eid":1}
{"pannum":"def","eid":3}
{"pannum":"abc","eid":2}
jsonStringList :: [{"pannum":"SCTPK7678T","eid":1}, {"pannum":"def","eid":3}, {"pannum":"abc","eid":2}]
 */
