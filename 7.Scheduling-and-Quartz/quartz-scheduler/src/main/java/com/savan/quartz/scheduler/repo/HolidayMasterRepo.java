package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.HolidayMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayMasterRepo extends JpaRepository<HolidayMaster, Long> {

    List<HolidayMaster> findByActive(Boolean active);

    List<HolidayMaster> findByCalendarIdAndActive(String  calendarId, Boolean active);
}
