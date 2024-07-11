package com.project.tasktracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //disable csrf and authentication
        //csrf was blocking POST and PUT requests
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
        return http.build();

    }



}
