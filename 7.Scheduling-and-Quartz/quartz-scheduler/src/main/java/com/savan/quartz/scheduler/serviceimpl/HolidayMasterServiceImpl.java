package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.HolidayMaster;
import com.savan.quartz.scheduler.repo.HolidayMasterRepo;
import com.savan.quartz.scheduler.service.IHolidayMasterService;
import com.savan.quartz.scheduler.utils.SchedulerUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class HolidayMasterServiceImpl implements IHolidayMasterService {

    @Autowired
    private SchedulerUtils schedulerUtils;

    @Autowired
    private HolidayMasterRepo holidayMasterRepo;

    @Override
    public List<HolidayMaster> findAll() {
        return holidayMasterRepo.findAll();
    }

    @Override
    public List<HolidayMaster> findByActive(Boolean active) {
        return holidayMasterRepo.findByActive(active);
    }

    @Override
    public List<HolidayMaster> findByCalendarIdAndActive(String  calendarId, Boolean active) {
        return holidayMasterRepo.findByCalendarIdAndActive(calendarId, active);
    }

    @Override
    public HolidayMaster addHoliday(HolidayMaster holiday) throws SchedulerException {
        HolidayMaster saved = holidayMasterRepo.save(holiday);
        if(holiday.isActive())
            schedulerUtils.addHolidayDate(saved.getCalendarId(), saved.getHolidayDate());
        return saved;
    }

    @Override
    public HolidayMaster updateHoliday(Long id, LocalDate newDate, boolean status) throws SchedulerException {
        HolidayMaster holiday = holidayMasterRepo.findById(id)
                                    .orElseThrow(() -> new RecordNotFoundException("Holiday with id "+id+", doesn't exists!"));
        LocalDate oldDate = holiday.getHolidayDate();

        holiday.setHolidayDate(newDate);
        holiday.setActive(status);
        HolidayMaster saved = holidayMasterRepo.save(holiday);
        if(holiday.isActive()) schedulerUtils.updateHolidayDate(saved.getCalendarId(), oldDate, newDate);
        if(!holiday.isActive()) schedulerUtils.removeHolidayDate(holiday.getCalendarId(), holiday.getHolidayDate());
        return saved;
    }

    @Override
    public String deleteHoliday(Long id) throws SchedulerException {
        HolidayMaster holiday = holidayMasterRepo.findById(id)
                                    .orElseThrow(() -> new RecordNotFoundException("Holiday with id "+id+", doesn't exists!"));
        holidayMasterRepo.delete(holiday);
        schedulerUtils.removeHolidayDate(holiday.getCalendarId(), holiday.getHolidayDate());
        return "Holiday deleted successfully!";
    }

    // Init holiday calendar on application start
    @PostConstruct
    public void initHolidayCalendar() throws SchedulerException {
        holidayMasterRepo.findAll().forEach(holiday -> {
            try {
                log.info("Initiating the holiday calender into the scheduler ...");
                if(holiday.isActive()) addHoliday(holiday);
                log.info("Initiated the holiday calender into the scheduler ...");
            } catch (SchedulerException e) {
                log.info("An exception occurred while initiating the holiday calender into the scheduler ...");
            }
        });
    }
}