package com.savan.quartz.comm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommDataRepo extends JpaRepository<CommData, Long> {

    List<CommData> findByStatus(String status);
}
