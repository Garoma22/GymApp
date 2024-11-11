package com.example.gymApp.config;


import com.example.gymApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableAspectJAutoProxy
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {

  private final UserRepository userRepository;

  //added userRepository to that
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  //The method returns an object of type AuthenticationProvider, which is used by Spring Security to authenticate users.
  public AuthenticationProvider authenticationProvider() {

    // built-in authentication provider in Spring Security that uses UserDetailsService to
    // retrieve user information and PasswordEncoder to validate passwords.
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    //Sets the UserDetailsService that will be used by the provider to load user information.
    authProvider.setUserDetailsService(userDetailsService());

    // Sets the PasswordEncoder that will be used to encrypt and verify passwords.
    // This method will compare the password entered by the user with the encrypted password stored in the database.
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }



  /*
  AuthenticationManager is the primary interface in Spring Security for user authentication.
  When a user attempts to authenticate (e.g. enters a username and password),
  the AuthenticationManager checks the credentials against one or more AuthenticationProviders
  (in this case, this could be, for example, a DaoAuthenticationProvider that works with user data from a database).
  If the credentials are correct, the AuthenticationManager returns an authentication object representing the authenticated user.
   */

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();

  }

  /*
  What BCryptPasswordEncoder does:
Password Encryption: When a user logs in, the password entered is encoded (hashed) using the
Crypt algorithm before being stored in the database.
Password Verification: When a user logs in, the password entered is verified against the
 encrypted value stored in the database. BCrypt automatically adds a "salt" (random salt) to each hash,
 making it more secure than hashing without a salt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}




