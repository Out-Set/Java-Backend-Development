package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupHeader;
import com.savan.quartz.scheduler.service.ISchedulerProcessGroupHeaderService;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/schedulerProcessGroupHeader")
public class SchedulerProcessGroupHeaderController {

    @Autowired
    private ISchedulerProcessGroupHeaderService schedulerProcessGroupHeaderService;

    @PostMapping("/create")
    public ResponseEntity<SchedulerProcessGroupHeaderDto> create(@RequestBody SchedulerProcessGroupHeader schedulerProcessGroupHeader){
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupHeaderService.create(schedulerProcessGroupHeader));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SchedulerProcessGroupHeaderDto> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupHeaderService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<SchedulerProcessGroupHeaderDto>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupHeaderService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SchedulerProcessGroupHeaderDto> update(@RequestBody SchedulerProcessGroupHeader schedulerProcessGroupHeader) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupHeaderService.update(schedulerProcessGroupHeader));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                schedulerProcessGroupHeaderService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
