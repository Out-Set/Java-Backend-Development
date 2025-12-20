package com.savan.quartz.scheduler.controller;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.scheduler.domainobject.HolidayMaster;
import com.savan.quartz.scheduler.service.IHolidayMasterService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/quartz/holidayMaster")
public class HolidayMasterController {

    @Autowired
    private IHolidayMasterService holidayMasterService;

    @GetMapping
    public ResponseEntity<List<HolidayMaster>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(holidayMasterService.findAll());
    }

    @GetMapping("/status/{active}")
    public ResponseEntity<List<HolidayMaster>> findByActive(@PathVariable Boolean active) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(holidayMasterService.findByActive(active));
    }

    @GetMapping("/calendarId/{calendarId}/status/{active}")
    public ResponseEntity<List<HolidayMaster>> findByCalendarIdAndActive(@PathVariable String  calendarId, @PathVariable Boolean active) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(holidayMasterService.findByCalendarIdAndActive(calendarId, active));
    }

    @PostMapping("/create")
    public ResponseEntity<HolidayMaster> addHoliday(@RequestBody HolidayMaster holiday) throws SchedulerException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(holidayMasterService.addHoliday(holiday));
    }

    @PutMapping("/update")
    public ResponseEntity<HolidayMaster> updateHoliday(@RequestParam Long id, @RequestParam LocalDate newDate, @RequestParam boolean active) throws SchedulerException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(holidayMasterService.updateHoliday(id, newDate, active));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteHoliday(@PathVariable Long id) throws SchedulerException {
        ResponseDto response = new ResponseDto(
                null,
                holidayMasterService.deleteHoliday(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
