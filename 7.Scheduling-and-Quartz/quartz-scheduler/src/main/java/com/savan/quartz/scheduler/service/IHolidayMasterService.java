package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.HolidayMaster;
import org.quartz.SchedulerException;

import java.time.LocalDate;
import java.util.List;

public interface IHolidayMasterService {

    List<HolidayMaster> findAll();

    List<HolidayMaster> findByActive(Boolean active);

    List<HolidayMaster> findByCalendarIdAndActive(String  calendarId, Boolean active);

    HolidayMaster addHoliday(HolidayMaster holiday) throws SchedulerException;

    HolidayMaster updateHoliday(Long id, LocalDate newDate, boolean active) throws SchedulerException;

    String deleteHoliday(Long id) throws SchedulerException;

}
