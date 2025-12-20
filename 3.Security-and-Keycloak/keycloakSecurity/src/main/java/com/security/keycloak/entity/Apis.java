package com.security.keycloak.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Apis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String apiName;
    private String description;
    private String serviceType;
    private String perHitPrice;

    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ClientApis> clientApisList;

}
