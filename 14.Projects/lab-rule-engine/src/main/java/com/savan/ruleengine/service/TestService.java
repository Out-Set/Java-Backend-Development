package com.savan.ruleengine.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.ruleengine.model.LabTest;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestService {

    private final RestHighLevelClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public TestService(RestHighLevelClient client) {
        this.client = client;
    }

    public String saveTest(LabTest labTest, String testIndex) throws Exception {
        Map<String, Object> testDoc = mapper.convertValue(labTest, new TypeReference<>() {});
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        testDoc.put("timestamp", LocalDateTime.now().toString(formatter));

        IndexRequest request = new IndexRequest(testIndex)
                .id(labTest.getPatientId())
                .source(testDoc, XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);
        return "Test saved successfully!";
    }

}
