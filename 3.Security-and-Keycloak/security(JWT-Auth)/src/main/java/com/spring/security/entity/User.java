package com.spring.security.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "users_tab")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;

	@NotBlank(message="name can't be blank")
	private String name;

	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format")
	@NotBlank(message="username can't be blank")
	@Column(name = "username", unique = true)
	private String username;

	@NotBlank(message="password can't be blank")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uid"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "rid"))
	private List<Role> roles;
}
