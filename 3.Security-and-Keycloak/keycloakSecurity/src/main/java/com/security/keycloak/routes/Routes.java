package com.security.keycloak.routes;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Route
        rest("/camelApi/verifyPAN")
                .post()
                .to("direct:callPanAPI");

        from("direct:callPanAPI")
                // Note: Remove the Authorization Header before calling other api(not needed here)
                .setHeader("Authorization", constant("no auth"))
                .to("http://194.233.84.104:8081/BitsFlow-App/rest/pan/verification?bridgeEndpoint=true")
                .log("Body :: ${body}");


        rest("/json/get")
                .get()
                .to("direct:jsonGet");

        from("direct:jsonGet")
                .process(exchange -> {
                    String jsonString = "{\"Summary - Scorecard\": [{\"Item\": \"Customer Name\", \"Details\": \"WYSETEK SYSTEMS TECHNOLOGISTS\", \"Verification\": \"NA\"}, ... (rest of the JSON string) ... ], \"Associated Party Transactions\": []}";

                    ObjectMapper mapper = new ObjectMapper();
                    Object jsonObject = mapper.readValue(jsonString, Object.class);
                    String prettyJsonString = mapper.writeValueAsString(jsonObject);
                    exchange.getIn().setBody(prettyJsonString);
                    System.out.println(prettyJsonString);
                })
                .log("Body :: ${body}");
    }
}

// Post-man
// 1. http://localhost:8080/camel/camelApi/verifyPAN with Token