package com.example.gymApp.controller;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import com.example.gymApp.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/registration")
public class RegisterUserFreeController {

  private final TraineeService traineeService;

  private final ProfileService profileService;

  private final TraineeMapper traineeMapper;

  private final TrainerService trainerService;

  private final TrainerMapper trainerMapper;


  public RegisterUserFreeController(TraineeService traineeService, ProfileService profileService,
      TraineeMapper traineeMapper, TrainerService trainerService, TrainerMapper trainerMapper) {
    this.traineeService = traineeService;
    this.profileService = profileService;
    this.traineeMapper = traineeMapper;
    this.trainerService = trainerService;

    this.trainerMapper = trainerMapper;
  }

  @PostMapping("/registerTrainee")
  public ResponseEntity<?> registerTrainee(@RequestBody TraineeDto traineeDto) {

    String username = profileService.generateUsername(traineeDto.getFirstName(),
        traineeDto.getLastName());
    String password = profileService.generateRandomPassword();

    Trainee trainee = traineeMapper.toTrainee(traineeDto);
    trainee.getUser().setUsername(username);
    trainee.getUser().setPassword(password);

    traineeService.createTrainee(
        trainee.getUser().getFirstName(),
        trainee.getUser().getLastName(),
        username,
        password,
        trainee.getDateOfBirth(),
        trainee.getAddress()
    );

    Map<String, String> response = new HashMap<>();
    response.put("username", username);
    response.put("password", password);

    return ResponseEntity.ok(response);
  }


  @PostMapping("/registerTrainer")
  public ResponseEntity<?> registerTrainer(@RequestBody TrainerDto trainerDto) {
    String username = profileService.generateUsername(trainerDto.getFirstName(),
        trainerDto.getLastName());
    String password = profileService.generateRandomPassword();

    trainerService.checkSpecializationCorrectness(
        trainerDto.getSpecialization());

    Trainer trainer = trainerMapper.toTrainer(trainerDto);
    trainer.getUser().setUsername(username);
    trainer.getUser().setPassword(password);

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

    return ResponseEntity.ok(response);
  }

}
