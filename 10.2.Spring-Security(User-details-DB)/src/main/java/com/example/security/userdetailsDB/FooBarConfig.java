package com.example.security.userdetailsDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;

@EnableWebSecurity
public class FooBarConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailService service;

    // Authenticate: Below lines tells spring that how you want to Authenticate
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }

    // Authorize: Below lines tells spring that how you want to Authorize
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers("/student/**").hasAnyAuthority("student", "admin")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }

    // BCrypt Password Encoder
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
