package com.demo.workflow.route;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LeaveManagementRoutes extends RouteBuilder {

    public static final int LEAVE_BALANCE = 5;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configure() throws Exception {

        from("direct:leaveBalanceCheck")
                .process(exchange -> {
                    if(LEAVE_BALANCE > 0){
                        logger.info("Employee have leave balance");
                    } else {
                        logger.info("Employee do not have leave balance");
                    }
                });


        from("direct:leaveBalanceDeduct")
                .process(exchange -> {
                    logger.info("Leave deducted successfully");
                });
    }
}
