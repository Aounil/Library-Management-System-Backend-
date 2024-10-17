package com.library.library.auth;

import com.library.library.service.emailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// will have 2 end points that will allow me to creat or register a new acc or authenticate an existing user
@RestController
@RequestMapping("/api/v1/auth")//It sets a base URL for all the endpoints defined in the authenticationController class. Any request mapped in this controller will start with /api/v1/auth.
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final emailService emailService;



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistreRequest request) {
        emailService.sendEmail(request.getEmail(),"NEW MEMBER !!","Welcome to our family "+request.getName());
        return ResponseEntity.ok(service.register(request));
    }




    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.Authenticate(request));
    }



}
