package com.library.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    //requestMatchers("") here u put ur white list which is endpoints that won't require tokens like login or creating acc
    //permitAll() u permet the access to all the Urls in the white list and anyRequest().authenticated() all the other request should be
    //authenticated (required a token)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()  // Auth endpoints permit it for all roles
                        .requestMatchers("/api/v1/demo-controller/admin/**").hasAuthority("ADMIN")  // Only ADMIN can access this
                        .requestMatchers("/api/v1/demo-controller/user/**").hasAuthority("USER")   // Only USER can access this
//                        .requestMatchers("/books/all").permitAll() // Allow access to /books/all without authentication
                        .anyRequest().authenticated()  // All other requests need authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS Configuration for Spring Security:
     *
     * When using Spring Security, the `@CrossOrigin` annotation alone is not sufficient
     * to enable CORS because Spring Security class overrides the default behavior of Spring MVC.
     *
     * To allow cross-origin requests (e.g., from http://localhost:3000), you must:
     * 1. Define a `CorsConfigurationSource` bean to specify allowed origins, methods, headers, and credentials.
     * 2. Enable CORS in the `HttpSecurity` configuration using `.cors()`.
     *
     * This ensures that:
     * - Preflight requests (OPTIONS) are allowed.
     * - The appropriate headers (e.g., `Access-Control-Allow-Origin`) are set.
     * - Credentials (e.g., cookies, authorization headers) are allowed if needed.
     *
     * Note: Once CORS is configured here, you do NOT need to use the `@CrossOrigin`
     * annotation in your controllers. This global configuration applies to all endpoints.
     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Allow frontend origin
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow specific HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Allow specific headers
        configuration.setAllowCredentials(true); // Allow credentials (e.g., cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS to all endpoints
        return source;
    }

}