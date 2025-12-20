package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.savan.quartz.scheduler.service.IJOBSchedulerExecutionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/jobSchedulerExecutionLog")
public class JOBSchedulerExecutionLogController {

    @Autowired
    private IJOBSchedulerExecutionLogService jobSchedulerExecutionLogService;

    @PostMapping("/create")
    public ResponseEntity<JOBSchedulerExecutionLog> create(@RequestBody JOBSchedulerExecutionLog jobSchedulerExecutionLog){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionLogService.create(jobSchedulerExecutionLog));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<JOBSchedulerExecutionLog> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionLogService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<Slice<JOBSchedulerExecutionLog>> read(
                                                            @RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "10") int pageSize
                                        ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionLogService.read(pageNumber, pageSize));
    }

    @PutMapping("/update")
    public ResponseEntity<JOBSchedulerExecutionLog> update(@RequestBody JOBSchedulerExecutionLog jobSchedulerExecutionLog) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionLogService.update(jobSchedulerExecutionLog));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                jobSchedulerExecutionLogService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
