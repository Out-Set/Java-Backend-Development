package com.savan.keycloak.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {

    private String username;
    private String email;
    private String isEmailVerified;
    private String password;
    private String firstName;
    private String lastName;
    private String realmRole;
}
