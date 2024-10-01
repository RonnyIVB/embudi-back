package com.satgy.embudi.security;

import com.satgy.embudi.service.UserPasswordServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurity {

    private final UserPasswordServiceImp passwordService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WebSecurity(UserPasswordServiceImp userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()//.authenticated()
                        // zzzz put the constraints
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)  throws Exception
    {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(this.passwordService)
                .passwordEncoder(this.bCryptPasswordEncoder)
                .and()
                .build();
    }




}