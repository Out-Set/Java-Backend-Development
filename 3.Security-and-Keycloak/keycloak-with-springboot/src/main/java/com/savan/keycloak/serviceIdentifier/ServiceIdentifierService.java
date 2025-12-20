package com.savan.keycloak.serviceIdentifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceIdentifierService {

    @Autowired
    private ServiceIdentifierRepository serviceIdentifierRepository;

    // Find by Code
    public ServiceIdentifier findByCode(String code) {
        return serviceIdentifierRepository.findByCode(code);
    }

}
