package com.example.gymApp.config;


import com.example.gymApp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration       //need it
//@EnableWebSecurity   // and it when we use spring boot 3.0
//@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//
//  private final JwtAuthenticationFilter jwtAuthFilter; // capture all requests and check JWT validity
//  private final AuthenticationProvider authenticationProvider; //is used to check and manage the authentication process.
//  // Typically, this is the bean responsible for checking the login and password.
//
//  //This method configures the security filter chain.
//  @Bean
//  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf().disable() //Disables CSRF (Cross-Site Request Forgery) protection,
//        // as JWT-based APIs typically do not require CSRF. JWT already provides request security.
//        .authorizeHttpRequests()//Enables request authorization settings for different requests
//        .requestMatchers("/auth/**").permitAll() //Allows unauthorized access to the specified routes.
//        .anyRequest().authenticated() //all other requests must be authenticated (require JWT)
//        .and()
//
//        //Configures the session management policy. In this case, STATELESS is specified, which means
//        // that the application will not use server sessions to store user state.
//        // All requests must contain a token (JWT) for authentication, and session state is not stored on the server.
//        .sessionManagement().sessioCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//
//        // Sets a custom authentication provider.
//        // This provider will be responsible for checking authentication data (e.g. login and password).
//        .authenticationPrivider(authenticationProvider)
//
//        //Adds a custom filter jwtAuthFilter to the security filter chain before the default UsernamePasswordAuthenticationFilter.
//        // This filter will intercept requests and validate the JWT before the default authentication is performed.
//        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//    return http.build();
//
//        /*
//        Output:
//       This code configures security for an application that uses JWT for authentication. It:
//
//        Disables CSRF.
//        Allows access to certain routes without authentication.
//        Requires authentication for all other requests.
//        Configures a filter to check for JWT.
//        Configures session management to be stateless.
//         */
//  }
//}


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
