package com.savan.keycloak.service;

import com.savan.keycloak.dto.KeycloakUser;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakUserService {

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String keycloakClientId;

    @Value("${keycloak.username}")
    private String keycloakUsername;

    @Value("${keycloak.password}")
    private String keycloakPassword;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(keycloakRealm)
                .clientId(keycloakClientId)
                .username(keycloakUsername)
                .password(keycloakPassword)
                .build();
    }

    public String createUser(KeycloakUser keycloakUser) {
        // Create user credentials
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setTemporary(false);
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(keycloakUser.getPassword());

        Keycloak keycloak = getKeycloakInstance();

        // Create user representation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(keycloakUser.getUsername());
        user.setEmail(keycloakUser.getEmail());
        user.setEmailVerified(Boolean.valueOf(keycloakUser.getIsEmailVerified()));
        user.setCredentials(Collections.singletonList(credentials));
        user.setFirstName(keycloakUser.getFirstName());
        user.setLastName(keycloakUser.getLastName());
        user.setRealmRoles(Arrays.asList(keycloakUser.getRealmRole().split(",")));
        user.setEnabled(true);

        try {
            // Create user in Keycloak
            Response response = keycloak.realm(keycloakRealm).users().create(user);

            if (response.getStatus() == 201) {
                return "User Created Successfully";
            } else if (response.getStatus() == 401) {
                return "Un-Authorized User";
            } else if (response.getStatus() == 403) {
                return "Request is Forbidden";
            } else if (response.getStatus() == 404) {
                return "Realm does not exists";
            } else if (response.getStatus() == 409) {
                return "User Already Exists";
            } else {
                throw new RuntimeException("Failed to create user - "+response.getStatus());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private UsersResource getUsersResource() {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realm = keycloak.realm(keycloakRealm);
        return realm.users();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    private RolesResource getRolesResource(){
        Keycloak keycloak = getKeycloakInstance();
        return  keycloak.realm(keycloakRealm).roles();
    }

    private UserRepresentation getUserByUsername(String username) {
        List<UserRepresentation> users = getUsersResource().search(username);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public String assignRole(String username, String roles) {

        UserRepresentation userRepresentation = getUserByUsername(username);
        String userId = "";
        if(userRepresentation != null){
            userId = userRepresentation.getId();
        } else {
            return "User does not exist";
        }

        String roleToBeAssign = "";
        try {
            // Assign roles to the user
            List<String> rolesList = Arrays.asList(roles.split(","));

            UserResource userResource = getUserResource(userId);
            RolesResource rolesResource = getRolesResource();

            if (userResource == null) {
                return "User with ID " + userId + " not found.";
            }

            List<RoleRepresentation> rolesRepresentation = new ArrayList<>();
            for(String role: rolesList) {
                roleToBeAssign = role;
                RoleRepresentation roleRepresentation = rolesResource.get(role).toRepresentation();
                rolesRepresentation.add(roleRepresentation);
            }
            userResource.roles().realmLevel().add(rolesRepresentation);
            return "Roles assigned successfully";
        } catch (Exception e) {
            System.out.println("An error occurred! "+e.getMessage());
            return "Realm-Role " + roleToBeAssign + " does not exits.";
        }
    }

}
