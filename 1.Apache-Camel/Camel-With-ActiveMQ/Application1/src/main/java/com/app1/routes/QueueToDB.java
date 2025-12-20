package com.app1.routes;

import com.app1.dto.userDtl;
import com.app1.entity.UserFullDtl;
import com.app1.service.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class QueueToDB extends RouteBuilder {

    @Autowired
    private Service service;

    @Override
    public void configure() throws Exception {

        // Get data from queue-3 and save it to userAllDtl
        from("activemq:queue-3").log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, userDtl.class)
                .process(exchange -> {
                    userDtl u1 = exchange.getIn().getBody(userDtl.class);
                    Long id = u1.getId();
                    String name = u1.getName();
                    String add = u1.getAdd();

                    System.out.println(id +" "+name+" "+add);

                    String jobTitle = "Developer";
                    int phoneNo = 1234656;
                    int sal = 135000;

                    UserFullDtl ufd = new UserFullDtl();
                    ufd.setId(id);
                    ufd.setName(name);
                    ufd.setAdd(add);
                    ufd.setJobTitle(jobTitle);
                    ufd.setPhoneNo(phoneNo);
                    ufd.setSal(sal);

                    service.save(ufd);

                    exchange.getMessage().setBody(ufd);

                })
                .marshal().json(JsonLibrary.Jackson)
                .to("file://C:\\Users\\admin\\Desktop\\FileRouter\\Output?fileName=singleUserDtl.json");

        // Receive single data from queue-4 then save it to UserAllDtl repo
        from("activemq:queue-4")
                .log("Data frm file is: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, UserFullDtl.class)
                .process(exchange -> {
                    UserFullDtl ufd = exchange.getIn().getBody(UserFullDtl.class);
                    service.save(ufd);

                    exchange.getMessage().setBody(ufd);
                })
                .marshal().json(JsonLibrary.Jackson)
                .log("${body}");


        // Receive list of data from queue-5 then save it to UserAllDtl repo
        from("activemq:queue-5")
                .log("Data frm file is: ${body}")
                .process(exchange -> {
                    ObjectMapper mapper = new ObjectMapper();
                    String json = exchange.getIn().getBody(String.class);

                    List<UserFullDtl> ufdList = mapper.readValue(json, new TypeReference<List<UserFullDtl>>() {
                    });

//                    for (UserFullDtl ufd : ufdList) {
//                        service.save(ufd);
//                    }
                    service.saveAll(ufdList);

                    exchange.getMessage().setBody(ufdList);
                })
                .marshal().json(JsonLibrary.Jackson)
                .to("file://C:\\Users\\admin\\Desktop\\FileRouter\\Output?fileName=ManyUsersDtl.json")
                .log("${body}")
                .to("activemq:topic:topic-1");
    }
}
