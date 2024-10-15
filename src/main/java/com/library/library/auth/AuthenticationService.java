package com.library.library.auth;

import com.library.library.User.User;
import com.library.library.User.UserRepository;
import com.library.library.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    //this register allows us to creat a user and save it to the database and return the generated token out off it
    public AuthenticationResponse register(RegistreRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    private RestTemplate restTemplate;

    // Define the URL of BRuno's error reporting endpoint
    private static final String BRUNO_ERROR_REPORTING_URL = "http://localhost:8080/reportError";

    public AuthenticationResponse Authenticate(AuthenticationRequest request) {
            // Attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


        // Fetch user from the repository
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token for the authenticated user
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}



