package com.savan.keycloak.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String GENERAL = "GENERAL";

    // Must for camel apis (i.e. /rest/**), otherwise 401 unauthorized each time unnecessarily
    @Value("${keycloak.admin.client.role}")
    public String keycloakClientRole;

    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers( "/test/anonymous/**", "keycloak/access-token", "/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/keycloak/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/keycloak/**").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.PUT, "/keycloak/**").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.DELETE, "/keycloak/**").hasRole(ADMIN)
//                        .requestMatchers("/rest/demo/**").hasRole(keycloakClientRole) //Must for camel rest apis
//                        .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.GET, "/test/user").hasAnyRole(GENERAL)
                        .anyRequest().authenticated());

        httpSecurity.sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

