package com.savan.ruleengine.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savan.ruleengine.model.LabRule;
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
public class RuleService {

    private final RestHighLevelClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public RuleService(RestHighLevelClient client) {
        this.client = client;
    }

    public String saveRule(LabRule labRule, String ruleIndex) throws Exception {
        Map<String, Object> ruleDoc = mapper.convertValue(labRule, new TypeReference<>() {});
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        ruleDoc.put("timestamp", LocalDateTime.now().toString(formatter));

        IndexRequest request = new IndexRequest(ruleIndex)
                .id(labRule.getId())
                .source(ruleDoc, XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);
        return "Rule saved successfully!";
    }

    public String executeRules(String ruleIndex, String testIndex) throws Exception {
        // 1. Fetch all active rules from lab_rules index
        var searchRequest = new org.opensearch.action.search.SearchRequest(ruleIndex);
        var searchSourceBuilder = new org.opensearch.search.builder.SearchSourceBuilder()
                .query(org.opensearch.index.query.QueryBuilders.termQuery("active", true))
                .sort("priority", org.opensearch.search.sort.SortOrder.ASC);
        searchRequest.source(searchSourceBuilder);
        var searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 2. Loop through each rule
        for (var hit : searchResponse.getHits().getHits()) {
            var rule = hit.getSourceAsMap();
            String ruleId = (String) rule.get("id");
            String testCode = (String) rule.get("testCode");
            String action = (String) rule.get("action");
            String expression = (String) rule.get("expression"); // Painless expression e.g. doc['result_value'].value < 100

            if ("AUTO_APPROVE".equalsIgnoreCase(action)) {
                // 3. Build dynamic UpdateByQueryRequest
                var updateRequest = new org.opensearch.index.reindex.UpdateByQueryRequest(testIndex);

                // Only match the test_code in the rule
                var boolQuery = org.opensearch.index.query.QueryBuilders.boolQuery()
                        .must(org.opensearch.index.query.QueryBuilders.termQuery("testCode.keyword", testCode))
                        .must(org.opensearch.index.query.QueryBuilders.scriptQuery(
                                new org.opensearch.script.Script(expression)
                        ));

                updateRequest.setQuery(boolQuery);

                // Build dynamic Painless script
                String scriptSource = String.format("""
                            ctx._source.autoApproved = true;
                            ctx._source.status = 'APPROVED';
                            ctx._source.approvedBy = 'RULE:%s';
                        """, ruleId);

                updateRequest.setScript(new org.opensearch.script.Script(scriptSource));

                // 4.Execute the rule
                client.updateByQuery(updateRequest, RequestOptions.DEFAULT);
            }
        }
        return "All active rules executed successfully!";
    }

}
