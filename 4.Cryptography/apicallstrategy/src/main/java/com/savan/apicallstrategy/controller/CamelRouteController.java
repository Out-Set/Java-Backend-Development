package com.savan.apicallstrategy.controller;

import com.savan.apicallstrategy.util.CryptoRequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamelRouteController extends RouteBuilder {

    @Autowired
    private CryptoRequestProcessor cryptoRequestProcessor;

    @Override
    public void configure() throws Exception {

        // AES Key encryption/decryption
        rest("/api/demo").get()
                .to("direct:demoRoute");
        from("direct:demoRoute")
                .log("Before api call ...")
                .bean(CryptoRequestProcessor.class, "performEncryption")
                .log("Request-Body before api call: ${body}")
                .log("Calling api ...")
                .bean(CryptoRequestProcessor.class, "preformDecryption")
                .log("Request-Body after api call: ${body}")
                .log("After api call ...");
    }
}
