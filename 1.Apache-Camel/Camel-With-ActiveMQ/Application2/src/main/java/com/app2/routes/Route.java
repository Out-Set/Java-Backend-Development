package com.app2.routes;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        rest("/sendToMq")
                .get()
                .to("direct:get-user-dtl");

        from("direct:get-user-dtl")
                .log("Rest API Calling ....")
                .doTry()
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .to("http://localhost:8094/userDtl?bridgeEndpoint=true")
                .to("activemq:queue-1")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String output = exchange.getIn().getBody(String.class);
                        System.out.println("Output is: "+output);
                    }
                })
                .doCatch(ExchangeTimedOutException.class)
                .log("Done")
                .log("Sent to activeMQ queue-1 ...");

        from("activemq:queue-2").log("${body}");



        // Send data to app-2 through queue-3, to save it into userAllDtl
        restConfiguration().component("servlet");

        rest("/sendToQueue3")
                .post()
                .to("direct:save-all-user-dtl");
        from("direct:save-all-user-dtl")
                .log("Rest API Calling ....")
                .doTry()
                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
                .to("http://localhost:8094/userDtl?bridgeEndpoint=true")
                .to("activemq:queue-3")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String output = exchange.getIn().getBody(String.class);
                        System.out.println("Output is: "+output);
                    }
                })
                .doCatch(ExchangeTimedOutException.class)
                .log("Done")
                .log("Sent to activeMQ queue-3 ...");



        // Get data from file and send it to queue-4 then save it
        from("file://C:\\Users\\admin\\Desktop\\FileRouter\\Input?noop=true")
                .filter(header(Exchange.FILE_NAME).startsWith("UserDtl"))
                .log("${body}")
                .to("activemq:queue-4");



        // Get list of data from file and send it to queue-5 then save it
        from("file://C:\\Users\\admin\\Desktop\\FileRouter?noop=true")
                .filter(header(Exchange.FILE_NAME).startsWith("ManyUsersDtl"))
                .log("${body}")
                .to("activemq:queue-5");


        // Send Email
        from("servlet://SendMail")
                .to("direct:sendEmail");

        from("direct:sendEmail")
//                .setHeader("To", constant("nikitaofficework7@gmail.com"))
//                .setHeader("Subject", constant("Testing"))
//                .setBody(constant("Have a nice day!"))
//                .to("smtps://savankrp@gmail.com")
//                .log("Mail Sent");


                .setHeader("To", constant("savankrp@gmail.com"))
                .setHeader("From", constant("nikitaofficework7@gmail.com"))
                .setHeader("Subject", constant("Your Subject"))
                .setBody(constant("Your email message content"))
                .to("smtp://smtp.gmail.com?username=nikitaofficework7@gmail.com&password=mbmcqljljeyoumiz&mail.smtp.starttls.enable=true")
                .log("Email sent successfully");
    }
}
