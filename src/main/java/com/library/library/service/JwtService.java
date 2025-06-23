package com.library.library.service;

import com.library.library.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//this file is used to extract the username from the token that's been passed from the JwtFilter


@Service
public class JwtService {
    private static final String SECRET_KEY = "4F40E8A7004B80A997891FA4189ADF9D216391D53424CE5B7E47AF37CCA83383";

// Before implementing this class, you need to add the following dependencies to your pom.xml file:
// jjwt-api, jjwt-impl, and jjwt-jackson.
// These are necessary for working with JWTs using the JJWT library to parse and handle JWT tokens.
//Use version 0.11.5 for the parserBuilder

// Claims are the pieces of information inside the JWT (JSON Web Token).
// These claims can include information like the username, roles, and any additional metadata.
// For example, a claim might be {"sub": "username", "role": "ADMIN"}.
// JWT claims are used to store and pass information securely between parties.


    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);//The Subject should be email or username of the user (username bcz of spring)
    }





    //This method is responsible for extracting all claims from the given JWT token and returns them as a Claims.
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //  This method extracts specific claims from the JWT token based on a provided function.
    // This allows for flexible extraction of any claim that you might need from the token.

    //Function<A, B> FFFF takes an A data type and convert it to a B data type based on the FFFF method
    // The function 'claimsResolver' takes a Claims object and extracts a specific value (like expiration or subject).
    // In this example, it could be Claims::getExpiration, Claims::getSubject, or any other method from the Claims class.
    //So claimResolver function like a placeholder for function that you want to use like GetExpiration...

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Apply the provided function to extract the desired claim
    }



    //if u want to generate a token from user details without extract claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //generate a token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        //had lclaims homa l7waj li katycodaw f token donc kayhzo role kaydiroh f token but in encrypted way
        extraClaims.put("role", ((User) userDetails).getRole());  // Add role to JWT claims
        extraClaims.put("name", ((User) userDetails).getName());  // Add name to it to use it in the navbar react
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  // 24-hour expiration
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //method that validate a token
    // we need the user details bcz we validate that the token belongs tot he user details

    public  boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration); //this is how u can extract the expiration date from a token
    }



    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
