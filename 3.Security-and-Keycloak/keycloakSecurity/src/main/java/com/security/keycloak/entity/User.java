package com.security.keycloak.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String firstName;
    private String lastName;

    @NotBlank(message = "Username is required")
    private String username;
    
    @Email(message = "Invalid email address")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference("user-client")
    private Client client;

}

