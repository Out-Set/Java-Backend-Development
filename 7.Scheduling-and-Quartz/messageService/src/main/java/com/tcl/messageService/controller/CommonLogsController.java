package com.tcl.messageService.controller;

import com.tcl.messageService.entity.CommonLogs;
import com.tcl.messageService.service.CommonLogsService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/logs")
public class CommonLogsController {

	// private static final Logger logger = LoggerFactory.getLogger(CommonLogsController.class);
    @Autowired
    private CommonLogsService commonLogsService;

    @GetMapping("/getCommonLogs")
    public List<CommonLogs> readAll(){
    	// logger.info("Logger-getLogs");
        return commonLogsService.readAll();
    }
}
