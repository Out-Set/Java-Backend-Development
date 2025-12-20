package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupDetail;
import com.savan.quartz.scheduler.service.ISchedulerProcessGroupDetailService;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz/schedulerProcessGroupDetail")
public class SchedulerProcessGroupDetailController {

    @Autowired
    private ISchedulerProcessGroupDetailService schedulerProcessGroupDetailService;

    @PostMapping("/create")
    public ResponseEntity<SchedulerProcessGroupDetailDto> create(@RequestBody SchedulerProcessGroupDetail schedulerProcessGroupDetail){
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupDetailService.create(schedulerProcessGroupDetail));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SchedulerProcessGroupDetailDto> readById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupDetailService.readById(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<SchedulerProcessGroupDetailDto>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupDetailService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<SchedulerProcessGroupDetailDto> update(@RequestBody SchedulerProcessGroupDetail schedulerProcessGroupDetail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schedulerProcessGroupDetailService.update(schedulerProcessGroupDetail));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long id) {
        ResponseDto response = new ResponseDto(
                null,
                schedulerProcessGroupDetailService.deleteById(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
