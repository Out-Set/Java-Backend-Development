package com.security.keycloak.repository;

import com.security.keycloak.entity.Apis;
import com.security.keycloak.entity.Client;
import com.security.keycloak.entity.ClientApis;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientApisRepo extends JpaRepository<ClientApis, Integer> {

    @Query(value = "select * from client_apis where api_id=:api_id", nativeQuery = true)
    ClientApis findByApiId(@Param("api_id") int api_id);

//    @Query(value = "select * from client_apis where api_id=:api_id and client_id=:client_id", nativeQuery = true)
//    ClientApis findByApiIdAndClientId(@Param("api_id") int api_id, @Param("client_id") int client_id);

    ClientApis findByApiAndClient(Apis apiId, Client client);
    List<ClientApis> findByClient(Client client);
}
