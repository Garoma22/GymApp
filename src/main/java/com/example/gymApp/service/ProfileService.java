package com.example.gymApp.service;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.model.Role;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final TraineeMapper traineeMapper;
  private final TraineeService traineeService;
  private final TrainerService trainerService;
  private final TrainerMapper trainerMapper;
  private final PasswordEncoder passwordEncoder;

  public String generateUsername(String firstName, String lastName) {
    String baseUsername = firstName + "." + lastName;

    List<String> existingUsernames = userRepository.findAllByUsernameStartingWith(
        baseUsername + "%");

    if (!existingUsernames.contains(baseUsername)) {
      return baseUsername;
    }

    int maxSuffix = existingUsernames.stream()
        .filter(username -> username.matches(baseUsername + "\\d+"))
        .map(username -> username.substring(baseUsername.length()))
        .mapToInt(suffix -> suffix.isEmpty() ? 0 : Integer.parseInt(suffix))
        .max()
        .orElse(0);

    return baseUsername + (maxSuffix + 1);
  }


  public String generateRandomPassword() {
    int length = 10;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();

    return IntStream.range(0, length)
        .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
        .collect(Collectors.joining());
  }

  public Map<String, String> registerTrainee(TraineeDto traineeDto) {

    String username = generateUsername(traineeDto.getFirstName(),
        traineeDto.getLastName());

//  String rawPassword = generateRandomPassword();
    String rawPassword = "password"; //need to have when we are checking authentication

    String encodedPassword = passwordEncoder.encode(rawPassword);

    traineeService.createTrainee(
        traineeDto.getFirstName(),
        traineeDto.getLastName(),
        username,
        encodedPassword,
        LocalDate.parse(traineeDto.getDateOfBirth()),
        traineeDto.getAddress()
    );

    // todo Probably need to respond only 200 ok here, not the rawPassword
    Map<String, String> response = new HashMap<>();
    response.put("username", username);
    response.put("password", rawPassword);

    return response;
  }

  public Map<String, String> registerTrainer(TrainerDto trainerDto) {
    trainerService.checkSpecializationCorrectness(trainerDto.getSpecialization());

    Trainer trainer = trainerMapper.toTrainer(trainerDto);

    String username = generateUsername(trainerDto.getFirstName(), trainerDto.getLastName());
    String password = generateRandomPassword();
    trainer.getUser().setUsername(username);
    trainer.getUser().setPassword(password);
    trainer.getUser().setRole(Role.TRAINER);

    trainerService.createTrainer(
        trainer.getUser().getFirstName(),
        trainer.getUser().getLastName(),
        trainer.getUser().getUsername(),
        trainer.getUser().getPassword(),
        trainer.getSpecialization().getName()
    );

    Map<String, String> response = new HashMap<>();
    response.put("username", username);
    response.put("password", password);

    return response;
  }
}
