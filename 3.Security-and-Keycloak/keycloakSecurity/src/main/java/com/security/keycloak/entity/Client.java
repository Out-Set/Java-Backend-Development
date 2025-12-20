package com.security.keycloak.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String address;
    private int totalUsedHits;
    private int totalRemainingHits;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference("user-client")
    private List<User> userList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ClientApis> clientApisList;

}
