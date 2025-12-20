package com.spring.service;


import com.spring.entity.Cron1Logs;
import com.spring.entity.Cron2Logs;
import com.spring.repository.Cron1LogsRepo;
import com.spring.repository.Cron2LogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronLogsService {

    @Autowired
    private Cron1LogsRepo cron1LogsRepo;
    public void saveCron1Logs(Cron1Logs cron1Logs){
        cron1LogsRepo.save(cron1Logs);
    }

    public List<Cron1Logs> getCron1Logs(){
        return cron1LogsRepo.findAll();
    }

    @Autowired
    private Cron2LogsRepo cron2LogsRepo;
    public void saveCron2Logs(Cron2Logs cron2Logs){
        cron2LogsRepo.save(cron2Logs);
    }

    public List<Cron2Logs> getCron2Logs(){
        return cron2LogsRepo.findAll();
    }
}
