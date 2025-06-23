package com.library.library.config;

import com.library.library.User.UserRepository;
import com.library.library.service.emailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // Marks this class as a Spring configuration class, which defines beans for the application.
@RequiredArgsConstructor  // Generates a constructor with required (final) fields, injecting the dependencies (in this case, UserRepository).
public class ApplicationConfig {

    // The repository used to interact with the database for user data.
    // It's injected via constructor because of @RequiredArgsConstructor.
    private final UserRepository userRepository;

    // Defines a bean for the UserDetailsService interface, which Spring Security uses to load user-specific data.
    // This bean provides a custom implementation of the loadUserByUsername method.
    @Bean
    public UserDetailsService userDetailsService() {
        // This is a lambda expression that overrides the loadUserByUsername method from UserDetailsService.
        // It looks for a user in the database by their email (email is considered a username)
        return username -> userRepository.findByEmail(username)// Uses the UserRepository to find the user by email.
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());// we need to tell the authprov which userDetailsService to use to get user details
        authProvider.setPasswordEncoder(passwordEncoder()); //in order to authenticate a user we need to know wich password Encoder to use
        return authProvider;
    }

    //The AuthenticationManager is needed to check if a user's credentials (like username and password) are valid.
    // It can also handle different authentication methods (like JWT, username/password, etc.).
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //returns which password Encoder to use
    }

}
