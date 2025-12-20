package com.spring.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
@Entity
@Table(name = "authorities_tab")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;

    @NotBlank(message="authority-name can't be blank")
    @Column(name = "authority_name", unique = true)
    private String authorityName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authorities")
    private List<Role> roles;
}
