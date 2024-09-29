package com.example.gymApp.controller;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeMapper;

import com.example.gymApp.dto.trainee.TraineeTrainingRequestDto;
import com.example.gymApp.dto.trainee.TraineeWithTrainerListDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerDto4fields;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.training.TrainingForTraineeResponseDto;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.User;
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import com.example.gymApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/trainee")
public class TraineeController {

  private static final Logger log = LoggerFactory.getLogger(TraineeController.class);
  private final TraineeService traineeService;
  private final UserService userService;
  private final ProfileService profileService;
  private final TraineeDto traineeDto;
  private final TraineeMapper traineeMapper;
  private final TrainerMapper trainerMapper;
  private final TrainerService trainerService;
  private final TrainingService trainingService;

  public TraineeController(TraineeService traineeService, UserService userService,
      ProfileService profileService, TraineeDto traineeDto,
      TraineeMapper traineeMapper, TrainerMapper trainerMapper, TrainerService trainerService,
      TrainingService trainingService) {
    this.traineeService = traineeService;
    this.userService = userService;
    this.profileService = profileService;
    this.traineeDto = traineeDto;
    this.traineeMapper = traineeMapper;
    this.trainerMapper = trainerMapper;

    this.trainerService = trainerService;
    this.trainingService = trainingService;
  }

  @GetMapping("/getAll")
  public List<Trainee> getAllTrainees() {
    return traineeService.getAllTrainees();
  }



  /*

  5. Get Trainee Profile (GET method)
a. Request
I. Username (required)
b. Response
I. First Name
II. Last Name
III. Date of Birth
IV. Address
V. Is Active
VI. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)
   */

  @GetMapping("/getTraineeWithTrainersList")
  public ResponseEntity<?> getTraineeProfileWithTrainersList(@RequestParam String username) {

    Trainee trainee = traineeService.getTraineeByUsername(username);

    List<TrainerDto> trainersList = trainerService.getAlltrainersByTrainee(username).stream()
        .map(trainer -> new TrainerDto(
            trainer.getUser().getUsername(),
            trainer.getUser().getFirstName(),
            trainer.getUser().getLastName(),
            trainer.getSpecialization().getName()))
        .collect(Collectors.toList());

    TraineeWithTrainerListDto responseDTO = new TraineeWithTrainerListDto(
        trainee.getUser().getUsername(),
        trainee.getUser().getFirstName(),
        trainee.getUser().getLastName(),
        trainee.getDateOfBirth(),
        trainee.getAddress(),
        trainee.getUser().isActive(),
        trainersList
    );

    return ResponseEntity.ok(responseDTO);
  }



/*
6. Update Trainee Profile (PUT method)
a. Request
I. Username (required)
II. First Name (required)
III. Last Name (required)
IV. Date of Birth (optional)
V. Address (optional)
VI. Is Active (required)


b. Response
I. Username
II. First Name
III. Last Name
IV. Date of Birth
V. Address
VI. Is Active
VII. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)
 */


  @PutMapping("/updateTrainee")
  public ResponseEntity<?> updateTraineeProfile(@RequestBody TraineeDto traineeDto,
      @RequestParam String username) {
    Trainee trainee = traineeService.getTraineeByUsername(username);
    System.out.println("Before Update: " + trainee);

    trainee.getUser().setFirstName(traineeDto.getFirstName());
    trainee.getUser().setLastName(traineeDto.getLastName());
    trainee.getUser().setActive(traineeDto.isActive());
    trainee.setDateOfBirth(LocalDate.parse(traineeDto.getDateOfBirth()));
    trainee.setAddress(traineeDto.getAddress());

    Trainee updatedTrainee = traineeService.updateTrainee(trainee,
        traineeDto.getFirstName(),
        traineeDto.getLastName(),
        trainee.getUsername(),

        trainee.getUser().getPassword(),
        traineeDto.isActive(),
        traineeDto.getAddress(),
        LocalDate.parse(traineeDto.getDateOfBirth()));

    List<TrainerDto> trainersList = trainerService.getAlltrainersByTrainee(username).stream()
        .map(trainer -> new TrainerDto(
            trainer.getUser().getUsername(),
            trainer.getUser().getFirstName(),
            trainer.getUser().getLastName(),
            trainer.getSpecialization().getName()))
        .collect(Collectors.toList());

    TraineeWithTrainerListDto responseDto = new TraineeWithTrainerListDto(
        updatedTrainee.getUser().getUsername(),
        updatedTrainee.getUser().getFirstName(),
        updatedTrainee.getUser().getLastName(),
        updatedTrainee.getDateOfBirth(),
        updatedTrainee.getAddress(),
        updatedTrainee.getUser().isActive(),
        trainersList
    );

    return ResponseEntity.ok(responseDto);
  }

/*
7. Delete Trainee Profile (DELETE method)
a. Request
I. Username (required)
b. Response
I. 200 OK0
 */

  @DeleteMapping("/deleteTrainee")
  public ResponseEntity<String> deleteTraineeProfile(@RequestParam String username) {
    Trainee trainee = traineeService.getTraineeByUsername(username);
    traineeService.deleteTraineeByUsername(trainee.getUsername());
    return ResponseEntity.ok("Trainee profile deleted successfully");
  }



  /*
10. Get not assigned on trainee active trainers. (GET method)
a. Request
I. Username (required) trainee
b. Response
I. Trainer Username
II. Trainer First Name
III. Trainer Last Name
IV. Trainer Specialization (Training type reference)
   */

  @GetMapping("/getNotAssignedActiveTrainersOnTrainee")
  public ResponseEntity<?> getNotAssignedActiveTrainersOnTrainee(
      @RequestParam String traineeUsername) {

      Trainee trainee = traineeService.getTraineeByUsername(traineeUsername);
      List<Trainer> trainers = trainerService.getAllActiveTrainersNotAssignedToTrainee(trainee);
      List<TrainerDto4fields> trainerDtos = TrainerMapper.INSTANCE.toTrainerDto4fieldsList(
          trainers);
      return ResponseEntity.ok(trainerDtos);
    }

  /*
  11. Update Trainee's Trainer List (PUT method)
a. Request
I. Trainee Username
II. Trainers List (required)
1. Trainer Username (required)
b. Response
I. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)
   */

  @PutMapping("/updateTraineeTrainersList")
  public ResponseEntity<?> updateTraineeTrainersList
      (@RequestParam String traineeUsername, @RequestBody List<String> newTrainersNames) {

      List<Trainer> foundedTrainers = trainerService.findByUsernameIn(newTrainersNames);
      Trainee trainee = traineeService.getTraineeByUsername(traineeUsername);
      trainingService.createTraining2args(foundedTrainers, trainee);
      List<Trainer> trainers = trainingService.getAllTrainersByTraineeUsername(
          traineeUsername);
      System.out.println(
          "All trainers by trainee " + trainee.getUsername() + " : " + trainers);

      List<TrainerDto4fields> trainerDtos = TrainerMapper.INSTANCE.toTrainerDto4fieldsList(
          trainers);

      return ResponseEntity.ok(trainerDtos);
  }


  /*
  12. Get Trainee Trainings List (GET method)
a. Request
   I. Username (required)
   II. Period From (optional)
   III. Period To (optional)
   IV. Trainer Name (optional)
   V. Training Type (optional)
b. Response
   I. Training Name
   II. Training Date
   III. Training Type
   IV. Training Duration
   V. Trainer Name
   */
//


/*
The method filters trainings based on the following criteria:

Date range: If periodFrom and periodTo are provided, it filters trainings within the specified range.
Trainer's first name: If trainerFirstName is provided, it filters trainings where the trainer's first name matches.
Trainer's specialization: If specializationName is provided, it filters trainings by the trainer's specialization.
 */

  @GetMapping("/getTraineeTrainingsListByDynamicCriteria")
  public ResponseEntity<?> getTraineeTrainingsListByDynamicCriteria(

      @RequestParam String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String trainerFirstName,
      @RequestParam(required = false) String specializationName,
      HttpServletRequest request) {

    String authenticatedUsername = (String) request.getAttribute("username");

    User authenticatedUser = userService.getUserByUsername(authenticatedUsername);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found.");
    }

    if (!authenticatedUsername.equals(username)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
    }


    TraineeTrainingRequestDto traineeTrainingRequestDto =
        traineeMapper.toDto(username, periodFrom, periodTo, trainerFirstName, specializationName);


      List<Training> trainings = trainingService.findTraineeTrainingsByUsername(username);

      //works!
      if (periodFrom != null && periodTo != null) {
        trainings = trainings.stream()
            .filter(t -> !t.getTrainingDate().isBefore(traineeTrainingRequestDto.getPeriodFrom())
                && !t.getTrainingDate()
                .isAfter(traineeTrainingRequestDto.getPeriodTo()))
            .collect(Collectors.toList());
      }

      if (trainerFirstName != null) {
        trainings = trainings.stream()
            .filter(t -> t.getTrainer().getUser().getFirstName().equals(trainerFirstName))
            .collect(Collectors.toList());
      }

      if (specializationName != null) {
        trainings = trainings.stream()
            .filter(t -> t.getTrainer().getSpecialization().getName().equals(specializationName))
            .collect(Collectors.toList());
      }

      List<TrainingForTraineeResponseDto> responseDtos = TrainingForTraineeMapper.INSTANCE.toDtoList(
          trainings);

      return ResponseEntity.ok(responseDtos);


    }
  }





