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


//Lombok’s @RequiredArgsConstructor automatically
// creates a constructor for all final fields (and fields annotated with @NonNull) in your class.
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

        // ✅ Attempt to authenticate the user using their email and password.
        // This line checks if the credentials are valid by:
        // - Loading the user from the DB using email (via UserDetailsService).
        // - Comparing the hashed password with the one in the DB.
        // - If credentials are wrong, it throws an exception (e.g., BadCredentialsException).
        // - If correct, the user is considered authenticated by Spring Security.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),      // user's email (used as username)
                        request.getPassword()    // user's password (plain text, compared securely)
                )
        );

        // ✅ Retrieve the user from the database using the provided email.
        // If the user doesn't exist, throw an exception.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Generate a JWT token for the authenticated user.
        // The token will include claims like the user's email and roles,
        // and will be signed using a secret key.
        var jwtToken = jwtService.generateToken(user);

        // ✅ Return the token to the client inside a response object.
        // The client will store and use this token in the Authorization header for future requests.
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



}



