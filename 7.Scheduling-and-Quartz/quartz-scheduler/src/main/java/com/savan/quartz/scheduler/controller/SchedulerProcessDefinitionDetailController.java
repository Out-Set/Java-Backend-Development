package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessDefinitionDetail;
import com.savan.quartz.scheduler.service.ISchedulerProcessDefinitionDetailService;
import com.savan.quartz.scheduler.vo.SchedulerProcessDefinitionDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/schedulerProcessDefinitionDetail")
public class SchedulerProcessDefinitionDetailController {

    @Autowired
    private ISchedulerProcessDefinitionDetailService schedulerProcessDefinitionDetailService;

    @PostMapping("/create")
    public ResponseEntity<SchedulerProcessDefinitionDetailDto> create(@RequestBody SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail){
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessDefinitionDetailService.create(schedulerProcessDefinitionDetail));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SchedulerProcessDefinitionDetailDto> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessDefinitionDetailService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<SchedulerProcessDefinitionDetailDto>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessDefinitionDetailService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SchedulerProcessDefinitionDetailDto> update(@RequestBody SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessDefinitionDetailService.update(schedulerProcessDefinitionDetail));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                schedulerProcessDefinitionDetailService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}

