package com.scentify.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF buat testing
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // semua endpoint bebas akses
            )
            .httpBasic(Customizer.withDefaults()); // pakai HTTP Basic (boleh diabaikan kalau permitAll)

        return http.build();
    }
}
