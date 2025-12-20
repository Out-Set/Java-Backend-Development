package com.app1.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//@Component
public class
CSVtoPOJOPartial extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Only selected filed from csv to json
        from("file://C:\\Users\\admin\\Desktop\\FileRouter\\Input?noop=true")
                .filter(header(Exchange.FILE_NAME).startsWith("input_cibil_rawdata.csv"))
                .process(exchange -> {
                    File file = exchange.getIn().getBody(File.class);

                    try (CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
                        List<String> properties = new ArrayList<>();

                        int i=0;
                        for (CSVRecord record : parser) {
                            String column56 = record.get(56); // Replace 56 with the appropriate column index
                            properties.add(column56);
                            System.out.println(i++ + " " +column56);
                        }
                        System.out.println("Parser: "+parser);

                        exchange.getIn().setBody(properties);
                    } catch (IOException e) {
                        // Handle the exception as needed
                        e.printStackTrace();
                    }
                })
                .log("Single Data from CSV: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("file://C:\\Users\\admin\\Desktop\\FileRouter\\Output?fileName=addresses.json");
    }
}
