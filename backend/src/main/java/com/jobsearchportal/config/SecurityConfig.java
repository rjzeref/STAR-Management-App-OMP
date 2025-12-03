package com.jobsearchportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://localhost:3003", "http://localhost:3004", "http://localhost:3005"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/candidates").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/companies").permitAll()
                        // Job applications - specific rules must come BEFORE general company/candidate rules
                        .requestMatchers(HttpMethod.POST, "/api/job-applications/**").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PUT, "/api/job-applications/**").hasAnyRole("CANDIDATE", "COMPANY")
                        .requestMatchers(HttpMethod.GET, "/api/job-applications/**").hasAnyRole("ADMIN", "CANDIDATE", "COMPANY")
                        // Admin has full read access to monitor the platform
                        .requestMatchers(HttpMethod.GET, "/api/companies/**", "/api/candidates/**", "/api/jobs/**").hasAnyRole("ADMIN", "CANDIDATE", "COMPANY")
                        .requestMatchers(HttpMethod.DELETE, "/api/companies/**", "/api/candidates/**", "/api/jobs/**", "/api/job-applications/**").hasRole("ADMIN")
                        // Company-specific permissions
                        .requestMatchers(HttpMethod.POST, "/api/companies/**").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.POST, "/api/jobs/**").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.PUT, "/api/companies/**").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.PUT, "/api/jobs/**").hasRole("COMPANY")
                        // Candidate-specific permissions
                        .requestMatchers(HttpMethod.POST, "/api/candidates/**").hasRole("CANDIDATE")
                        .requestMatchers(HttpMethod.PUT, "/api/candidates/**").hasRole("CANDIDATE")
                        // Admin endpoints
                        .requestMatchers("/api/admins/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
