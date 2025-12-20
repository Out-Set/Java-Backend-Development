package com.tcl.messageService.repository;

import com.tcl.messageService.entity.CustomCommConfigMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCommConfigMstRepo extends JpaRepository<CustomCommConfigMst, Integer> {

    @Query(value = "select * from custom_comm_config_mst where communication_type_name=:comm_type_name", nativeQuery = true)
    public CustomCommConfigMst findByCommTypeName(@Param("comm_type_name") String comm_type_name);

    @Query(value = "select * from custom_comm_config_mst where communication_type_name=:comm_type_name AND status=:status", nativeQuery = true)
    public CustomCommConfigMst findByCommTypeNameAndStatus(@Param("comm_type_name") String comm_type_name, @Param("status") String status);

    @Query(value = "select * from custom_comm_config_mst where communication_type=:comm_type and communication_type_name=:comm_type_name and status=:status", nativeQuery = true)
    public List<CustomCommConfigMst> findByCommTypeAndCommTypeNameAndStatus(@Param("comm_type") String comm_type, @Param("comm_type_name") String comm_type_name, @Param("status") String status);
}
