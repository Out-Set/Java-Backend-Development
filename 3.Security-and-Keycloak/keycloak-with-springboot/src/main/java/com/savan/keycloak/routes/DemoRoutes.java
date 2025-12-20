package com.savan.keycloak.routes;

import com.savan.keycloak.configs.JwtAuthConverter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoRoutes extends RouteBuilder {

    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    @Override
    public void configure() throws Exception {

        rest("/demo/route").get()
                .routeId("DEMO")
                .to("direct:demoRoute");

        from("direct:demoRoute")
                .process(exchange -> {
                    String username = jwtAuthConverter.getLoggInUserName();
                    List<String> roles = jwtAuthConverter.getRoles();
                    if(roles.contains(exchange.getFromRouteId())){
                        exchange.getIn().setBody("Hello: "+username+", having roles: "+roles);
                    } else {
                        exchange.getIn().setBody("Hello: "+username+", you are not authorized to access this api.");
                    }
                });
    }
}
