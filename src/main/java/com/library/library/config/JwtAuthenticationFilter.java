package com.library.library.config;

import com.library.library.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor//creat a constructor using any final key word that we declared here

//The general purpose of this JwtAuthenticationFilter is to authenticate users based on a JWT token before allowing them access to any secured resources (like API endpoints).
// Here's a simplified explanation of what it does:

//Intercepts HTTP Requests: This filter runs on every HTTP request to check if the request includes a JWT (JSON Web Token) in the Authorization header.

//Validates the JWT: If the token is present and valid, it extracts the user's information (like email or username) from the token.

//Authenticates the User: If the user is not already authenticated (i.e., not logged in yet), the filter will:

//Load user details from the database.
//Check if the token matches the user details.
//Set the user's authentication into the SecurityContext, which means the user is now recognized as authenticated for this request.
//Passes the Request Along: Once the filter validates the token and authenticates the user, it allows the request to continue to its destination (e.g., a controller or another filter). If the token is invalid or missing, it stops the request from proceeding.

//evry time we get a http request this filter will get activated by the extents
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");//Authorization is the name of the header that contains the Bearer token
        String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) //Bearee token awlyas start with Bearer A bearer token means that the token itself grants access to a resource.
        // Whoever holds (or "bears") the token can access the protected resource without needing to prove any additional credentials.
        {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);//"bearer" fiha 7 token starts after it
        userEmail = jwtService.extractUsername(jwt);

        //If SecurityContextHolder.getContext().getAuthentication() == null is true, it means that the user is not authenticated. In other words,
        // no valid authentication information is available in the security context, and the user hasn't logged in or hasn't been authenticated.

        //explination for what did we do here

        //if we have our user email and the User is not authenticated,  we get the user detail from the database using the loadUserByUsername
        //then we check if the user is valid or not if the user and the token are valid we create an object of type UsernamePasswordAuthenticationToken
        //pass the user details and authorities as param then we enforce our token with the detail off our request then we update
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);//in the userDetails interface, the loadUserByUsername has been overridden in the app config by using a lambda expression
            if(jwtService.isTokenValid(jwt,userDetails)) {
                //if the request is valid sent updates to the security context and send the request to the dispatcher
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);//update the security holder

            }

        }
        // After all our checks (like verifying the JWT token), we need to let the request
        // continue to the next filter or eventually reach the controller (the final destination).
        //This method lets us make the request go forward into The next step
        // If we don't call this, the request stops here and doesn't move forward,
        // meaning the user won't be able to access the resource they're requesting.
        filterChain.doFilter(request, response);

    }
}

