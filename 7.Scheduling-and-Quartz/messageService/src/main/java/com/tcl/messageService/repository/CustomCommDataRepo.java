package com.tcl.messageService.repository;

import com.tcl.messageService.entity.CustomCommData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface CustomCommDataRepo extends JpaRepository<CustomCommData, Integer> {

    @Query(value = "select * from custom_comm_data where communication_type =:communicationType and status =:status", nativeQuery = true)
    public List<CustomCommData> findByStatus(@Param("communicationType")String communicationType, @Param("status")String status);
}
