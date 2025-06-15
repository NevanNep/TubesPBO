package com.scentify.backend.config;

import com.scentify.backend.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // ✅ Endpoint publik (akses tanpa token)
                .requestMatchers(
                    "/api/auth/**",
                    "/api/products",
                    "/api/products/**",
                    "/api/admin/register",
                    "/api/buyer/register"
                ).permitAll()

                // ✅ Endpoint hanya untuk admin
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ Endpoint hanya untuk buyer
                .requestMatchers("/api/cart/**").hasRole("BUYER")

                // ✅ Endpoint lain memerlukan token
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // ✅ Pasang filter JWT sebelum filter bawaan Spring
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
