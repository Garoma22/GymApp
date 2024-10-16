package com.example.gymApp.service;

import com.example.gymApp.authentification.AuthenticationRequest;
import com.example.gymApp.authentification.AuthenticationResponse;
import com.example.gymApp.authentification.RegisterRequest;
import com.example.gymApp.model.Role;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

//  public boolean authenticate(String username, String password, HttpServletRequest request) {
//    User user = userService.getUserByPasswordAndUsername(username, password);
//    if (user != null) {
//      HttpSession session = request.getSession();
//      session.setAttribute("user", user);
//      return true;
//    }
//    return false;
//  }


  //method register user in DB and return the generated token
  //here we build user from RegisterRequest
  public AuthenticationResponse register(RegisterRequest request) {

    var user = User.builder()

        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse
        .builder()
        .token(jwtToken)
        .build();
  }


  //here we are checking login and password
  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    /*
    authenticationManager is an object of type AuthenticationManager that manages the authentication
    process. It compares the provided credentials with the data stored in the system (e.g., a database)
    using the configured UserDetailsService and PasswordEncoder.
     */

    authenticationManager.authenticate(

        //check username and password
        new UsernamePasswordAuthenticationToken( //this class is used to pass credentials
            // (username and password) to the authentication system.
            request.getUsername(),
            request.getPassword()

            // This token contains a username and password that will later be verified
            // against data stored in a database or other storage.
        )
    );


    /*
    General process:
    After successful authentication of the user, his data (username) is used to search the database via userRepository.
    If the user is found, a JWT token is generated for him using jwtService.
    The generated token is returned to the client as part of the AuthenticationResponse object.
    The client can use this token to access protected resources by passing it in the request header.
     */

    var user = userRepository.findByUsername(request.getUsername())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse
        .builder()
        .token(jwtToken)
        .build();
  }
}
