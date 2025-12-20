package com.security.keycloak.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClientApis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "api_id")
    @JsonBackReference("api")
    private Apis api;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference("client")
    private Client client;

    private int remainingHitsOfApi;
    private int consumedHitsOfApi;

}
