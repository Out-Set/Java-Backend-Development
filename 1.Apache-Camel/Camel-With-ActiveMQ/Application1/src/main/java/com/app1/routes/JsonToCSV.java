package com.app1.routes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

//@Component
public class JsonToCSV extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Convert JSON to CSV
        // Scheduling a task to run in each 5-sec, which hits an api and saves the json data to local file
        // then from local file to csv conversion, and then csv to local file

        from("quartz://myGroup/myTimerName?cron=0/5+*+*+*+*+?")
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .to("http://localhost:8092/userFullDtl?bridgeEndpoint=true")
                .log("Data-1 is: ${body}")
                .process(exchange -> {
                    int i=0;
                    // Convert JSON to CSV using a processor
                    JsonNode jsonTree = new ObjectMapper().readTree(exchange.getIn().getBody(String.class));
                    CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
                    JsonNode firstObject = jsonTree.elements().next();
                    firstObject.fieldNames().forEachRemaining(fieldName -> {
                        csvSchemaBuilder.addColumn(fieldName);
                    });
                    CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

                    CsvMapper csvMapper = new CsvMapper();
                    csvMapper.writerFor(JsonNode.class)
                            .with(csvSchema)
                            .writeValue(new File("C:\\Users\\admin\\Desktop\\FileRouter\\Output\\data${i++}.csv"), jsonTree);
                    // Now, data.csv can be sent anywhere

                    exchange.getIn().setBody(jsonTree);
                })
                .log("log:info: ${body}");
    }
}
