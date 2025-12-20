package com.spring.repository;

import com.spring.entity.Cron1Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cron1LogsRepo extends JpaRepository<Cron1Logs, Integer> {

}
