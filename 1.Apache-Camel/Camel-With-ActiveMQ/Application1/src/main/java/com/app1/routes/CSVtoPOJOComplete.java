package com.app1.routes;

import com.app1.entity.UserFullDtl;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

//@Component
public class CSVtoPOJOComplete extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // All filed from csv to json
        from("file://C:\\Users\\admin\\Desktop\\FileRouter\\Input?noop=true")
                .filter(header(Exchange.FILE_NAME).startsWith("data.csv"))
                .process(exchange -> {
                    File csvFile = exchange.getIn().getBody(File.class);

                    CsvMapper csvMapper = new CsvMapper();

                    CsvSchema csvSchema = csvMapper
                            .typedSchemaFor(UserFullDtl.class)
                            .withHeader()
                            .withColumnSeparator(',')
                            .withComments();

                    MappingIterator<UserFullDtl> userFullDtlIter = csvMapper
                            .readerWithTypedSchemaFor(UserFullDtl.class)
                            .with(csvSchema)
                            .readValues(csvFile);

                    List<UserFullDtl> userFullDtl = userFullDtlIter.readAll();

                    exchange.getIn().setBody(userFullDtl);
                })
                .log("Data from CSV: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to("file://C:\\Users\\admin\\Desktop\\FileRouter\\Output?fileName=data.json");



    }
}
