package com.spring.security.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Token> tokens;
}
