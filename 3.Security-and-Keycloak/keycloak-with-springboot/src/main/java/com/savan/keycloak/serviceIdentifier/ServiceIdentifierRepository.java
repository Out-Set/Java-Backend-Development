package com.savan.keycloak.serviceIdentifier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceIdentifierRepository extends JpaRepository<ServiceIdentifier, Long> {

    ServiceIdentifier findByCode(String code);

    @Query("Select s.name from ServiceIdentifier s where  s.genericService = true")
    List<String> getAllServices();

    @Query("Select s.code from ServiceIdentifier s where  s.genericService = true")
    List<String> getAllGenericServices();

    @Query("Select s.id from ServiceIdentifier s where s.code =:serviceType")
    Long findIdByCode(String serviceType);

}
