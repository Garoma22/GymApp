package com.example.gym.service;

import com.example.gym.authentification.AuthenticationRequest;
import com.example.gym.authentification.AuthenticationResponse;
import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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

  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    String username = request.getUsername();

    if (loginAttemptService.isBlocked(username)) {
      throw new RuntimeException("User is blocked. Try again in "
          + loginAttemptService.getRemainingBlockTime(username) + " minutes.");
    }

    try {

      authenticationManager.authenticate(

          new UsernamePasswordAuthenticationToken(
              request.getUsername(),
              request.getPassword()


          )
      );
      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

      var jwtToken = jwtService.generateToken(userDetails);

      return AuthenticationResponse
          .builder()
          .token(jwtToken)
          .build();
    }

        catch (BadCredentialsException e) {
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
