package com.tcl.messageService.repository;

import com.tcl.messageService.entity.CommonLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonLogsRepo extends JpaRepository<CommonLogs, Integer> {

}
