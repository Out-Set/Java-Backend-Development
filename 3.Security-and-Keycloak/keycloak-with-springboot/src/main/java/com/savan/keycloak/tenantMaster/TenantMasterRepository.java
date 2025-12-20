package com.savan.keycloak.tenantMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TenantMasterRepository extends JpaRepository<TenantMaster, Long> {

    TenantMaster findByCode(String code);

    @Query("Select s.code from TenantMaster s")
    List<String> allTenantMasters();

    @Query("select s.id from TenantMaster s where s.code=:code")
    Long findIdByCode(String code);

    @Query("select s from TenantMaster s where s.id = :tenantMasterId")
    TenantMaster findByTenantMasterId(Long tenantMasterId);
}
