package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionMappingDetail;
import com.savan.quartz.scheduler.service.IJOBSchedulerExecutionMappingDetailService;
import com.savan.quartz.scheduler.vo.JOBSchedulerExecutionMappingDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/jobSchedulerExecutionMappingDetail")
public class JOBSchedulerExecutionMappingDetailController {

    @Autowired
    private IJOBSchedulerExecutionMappingDetailService jobSchedulerExecutionMappingDetailService;

    @PostMapping("/create")
    public ResponseEntity<JOBSchedulerExecutionMappingDetailDto> create(@RequestBody JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail){
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionMappingDetailService.create(jobSchedulerExecutionMappingDetail));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<JOBSchedulerExecutionMappingDetailDto> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionMappingDetailService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<JOBSchedulerExecutionMappingDetailDto>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionMappingDetailService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<JOBSchedulerExecutionMappingDetailDto> update(@RequestBody JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(jobSchedulerExecutionMappingDetailService.update(jobSchedulerExecutionMappingDetail));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                jobSchedulerExecutionMappingDetailService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
