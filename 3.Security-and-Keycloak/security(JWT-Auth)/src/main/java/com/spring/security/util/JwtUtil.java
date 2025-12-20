package com.spring.security.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.spring.security.serviceImpl.UserServiceImpl;
import com.spring.security.entity.Authority;
import com.spring.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secret;

	@Autowired
	private UserServiceImpl userService;
	
	//Code that gets the subject information(subject: username)
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	// Generate Token
	public String generateToken(String subject) {
		List<Role> roles = userService.findByUsername(subject).getRoles();
		List<String> extractedRoles = roles.stream().map(Role::getRoleName).toList();
		List<String> extractedAuthorities = roles.stream()
				.flatMap(role -> role.getAuthorities().stream())
				.map(Authority::getAuthorityName)
				.distinct()
				.toList();
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", extractedRoles);
		claims.put("authorities", extractedAuthorities);
		System.out.println("claims: "+claims);
		return generateToken(claims, subject);
	}
	
	// Get Claims
	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token).getBody();
	}
	

	// Code that generates the token(subject: username)
	private String generateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuer("api-flow")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes())
				.compact();
	}
}