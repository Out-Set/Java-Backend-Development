package com.spring.repository;

import com.spring.entity.Cron2Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cron2LogsRepo extends JpaRepository<Cron2Logs, Integer> {

}
