package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessErrorLogDetail;
import com.savan.quartz.scheduler.service.ISchedulerProcessErrorLogDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/schedulerProcessErrorLogDetail")
public class SchedulerProcessErrorLogDetailController {

    @Autowired
    private ISchedulerProcessErrorLogDetailService schedulerProcessErrorLogDetailService;

    @PostMapping("/create")
    public ResponseEntity<SchedulerProcessErrorLogDetail> create(@RequestBody SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail){
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessErrorLogDetailService.create(schedulerProcessErrorLogDetail));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SchedulerProcessErrorLogDetail> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessErrorLogDetailService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<SchedulerProcessErrorLogDetail>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessErrorLogDetailService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SchedulerProcessErrorLogDetail> update(@RequestBody SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessErrorLogDetailService.update(schedulerProcessErrorLogDetail));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                schedulerProcessErrorLogDetailService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
