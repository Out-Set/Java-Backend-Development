package com.savan.keycloak.tenantMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantMasterService {

    @Autowired
    private TenantMasterRepository tenantMasterRepository;

    // Find by code
    public TenantMaster findByCode(String code) {
        return tenantMasterRepository.findByCode(code);
    }

    // Find by id
    public TenantMaster findByTenantMasterId(Long id) {
        return tenantMasterRepository.findByTenantMasterId(id);
    }

    // Get All Source System Identifiers
    public List<TenantMaster> allSources(){
        return tenantMasterRepository.findAll();
    }
}
