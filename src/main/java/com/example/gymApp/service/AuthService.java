package com.example.gymApp.service;

import com.example.gymApp.authentification.AuthenticationRequest;
import com.example.gymApp.authentification.AuthenticationResponse;
import com.example.gymApp.authentification.RegisterRequest;
import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.model.Role;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
  private final ProfileService profileService;
  private final UserDetailsService userDetailsService;
  private final LoginAttemptService loginAttemptService;

  //here we are checking login and password
  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    String username = request.getUsername();

    // check of blocking
    if (loginAttemptService.isBlocked(username)) {
      throw new RuntimeException("User is blocked. Try again in "
          + loginAttemptService.getRemainingBlockTime(username) + " minutes.");
    }

    try {

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

      // Use UserDetailsService for getting user from DB
      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

      //token generation from UserDetails object
      var jwtToken = jwtService.generateToken(userDetails);

      return AuthenticationResponse
          .builder()
          .token(jwtToken)
          .build();
    }

        catch (BadCredentialsException e) {
      // attempt counter ++
      loginAttemptService.loginFailed(username);
      throw e;
    }

  }

  public AuthenticationResponse registerTrainee(TraineeDto traineeDto) {
    Map<String, String> registrationData = profileService.registerTrainee(traineeDto);
    var user = userRepository.findByUsername(registrationData.get("username"))
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  public AuthenticationResponse registerTrainer(TrainerDto trainerDto) {
    Map<String, String> registrationData = profileService.registerTrainer(trainerDto);

    var user = userRepository.findByUsername(registrationData.get("username"))
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

}
