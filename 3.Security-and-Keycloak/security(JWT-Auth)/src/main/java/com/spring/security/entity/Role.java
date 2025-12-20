package com.spring.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "roles_tab")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    @NotBlank(message="role-name can't be blank")
    @Column(name = "role_name", unique = true)
    private String roleName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "rid"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "aid"))
    private List<Authority> authorities;
}

