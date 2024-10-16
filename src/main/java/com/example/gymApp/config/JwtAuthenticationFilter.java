package com.example.gymApp.config;

import com.example.gymApp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, // Intercepts every incoming HTTP request.
      HttpServletResponse response,
      FilterChain filterChain)  // Allows passing the request further down the filter chain.
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    // Retrieves the Authorization header from the request. Header contains token, name and password

    final String jwt;  // Variable to hold the JWT token.
    final String username; //need to validate user

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      // If the Authorization header is missing or does not start with "Bearer ", the request is passed through the filter chain without any processing.
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    // Extracts the JWT token by removing the "Bearer " prefix from the Authorization header.
    username = jwtService.extractUsername(jwt);

    //check is user Authenticated - SecurityContextHolder stores security information about the current thread (such as authentication data).
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // loadUserByUsername(username) returns a UserDetails object that contains information about the user,
      // such as their username, password, and permissions (roles).
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenWalid(jwt, userDetails)) { //if token walid

        //Here, a UsernamePasswordAuthenticationToken object is created that represents the authenticated user
        // in the Spring Security context. This object stores:
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, //info about user
            null, //no need password, because user already authenticated by token
            userDetails.getAuthorities() //rights and roles(!)
        );

        // This is where you add additional authentication details to the authToken. The buildDetails(request) method extracts
        // information from the request, such as the IP address or session data, and adds it to the authentication object.
        // This helps store additional request details that may be needed for security purposes.
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken); //final
      }
    }


    /*
    After all the logic inside doFilterInternal() has been executed (checking the JWT token, authenticating the user, etc.),
    filterChain.doFilter(request, response) is called, allowing the request to continue its journey through the filter chain
    and eventually reach the controller, which will process the request and return the response.
     */
    filterChain.doFilter(request, response);
  }
}

/*
example of request:

POST /api/login HTTP/1.1
Host: example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json
Content-Length: 123

 */