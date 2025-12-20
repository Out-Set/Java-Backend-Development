package com.tcl.messageService.service;

import com.tcl.messageService.entity.CommonLogs;
import com.tcl.messageService.repository.CommonLogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CommonLogsService {

    @Autowired
    private CommonLogsRepo commonLogsRepo;

    public List<CommonLogs> readAll(){
        return commonLogsRepo.findAll();
    }
}
