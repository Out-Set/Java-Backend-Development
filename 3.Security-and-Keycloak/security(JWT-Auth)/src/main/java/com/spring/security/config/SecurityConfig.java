package com.spring.security.config;

import com.spring.security.filter.SecurityFilter;
import com.spring.security.noauthservice.NoAuthApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PublicEndPoints publicEndPoints;

	@Autowired
	private NoAuthApiService noAuthApiService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private SecurityFilter securityFilter;

	@Value("${open.api}")
	private List<String> openEndpoints;
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	 DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	
    @Bean
    SecurityFilterChain configureAuth(HttpSecurity http) throws Exception {
		// All no-auth load working
		// String[] noAuthEndPoints = publicEndPoints.getPublicEndPoints().toArray(new String[0]); // loaded from code
		// String[] noAuthEndPoints = noAuthApiService.getEndPoints().toArray(new String[0]); // loaded from db
		String[] noAuthEndPoints = openEndpoints.toArray(new String[0]); // loaded from properties
		log.info("No auth endpoints: {}", Arrays.toString(noAuthEndPoints));
	return
    	http
        // Disable CSRF as we're working with stateless JWT tokens
        .csrf(AbstractHttpConfigurer::disable)
        
        // Configure endpoint access
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/**", "/role/**", "/authority/**", "/role-authority/**").permitAll() // Public endpoints
				// .requestMatchers("/camel/demo/route").permitAll()
				.requestMatchers(noAuthEndPoints).permitAll()
				// Allow preflight must for camel apis
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/product/read", "/product/read/**").hasAuthority("READ")
				.requestMatchers("/product/create").hasAuthority("CREATE")
				.requestMatchers("/product/update").hasAuthority("UPDATE")
				.requestMatchers("/product/delete/**").hasAuthority("DELETE")
				.anyRequest().authenticated() // Secure all other endpoints
        )

        // Exception handling configuration
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(authenticationEntryPoint)
        )
        
        //Stateless session management (for JWT-based authentication)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        
        // Add custom security filter before UsernamePasswordAuthenticationFilter
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        
        .build();

	}
}