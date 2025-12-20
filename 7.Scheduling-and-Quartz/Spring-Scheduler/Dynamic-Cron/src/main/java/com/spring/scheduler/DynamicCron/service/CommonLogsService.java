package com.spring.scheduler.DynamicCron.service;

import com.spring.scheduler.DynamicCron.entity.CommonLogs;
import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.repository.CommonLogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CommonLogsService {

    @Autowired
    private CommonLogsRepo commonLogsRepo;

    public void saveCommonLogs(CommonLogs commonLogs){
        commonLogsRepo.save(commonLogs);
    }

    public List<CommonLogs> getAllCommonLogs(){
        return commonLogsRepo.findAll();
    }

}
