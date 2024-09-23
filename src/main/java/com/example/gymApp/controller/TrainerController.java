package com.example.gymApp.controller;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeDto3fields;
import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerTrainingRequestDto;
import com.example.gymApp.dto.trainer.TrainerWithTraineeListDto;
import com.example.gymApp.dto.training.TrainingForTrainerResponseDto;
import com.example.gymApp.dto.trainingType.TrainingForTrainerMapper;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

  private static final Logger log = LoggerFactory.getLogger(TrainerController.class);
  private final TrainerService trainerService;
  private final ProfileService profileService;
  private final TrainerMapper trainerMapper;
  private final TraineeDto traineeDto;
  private final TraineeService traineeService;
  private final TraineeMapper traineeMapper;
  private final TrainingService trainingService;


  public TrainerController(TrainerService trainerService, ProfileService profileService,
      TrainerMapper trainerMapper, TraineeDto traineeDto, TraineeService traineeService,
      TraineeMapper traineeMapper, TrainingService trainingService) {
    this.trainerService = trainerService;
    this.profileService = profileService;
    this.trainerMapper = trainerMapper;
    this.traineeDto = traineeDto;
    this.traineeService = traineeService;
    this.traineeMapper = traineeMapper;
    this.trainingService = trainingService;
  }

  @PostMapping("/registerTrainer")
  public ResponseEntity<?> registerTrainer(@RequestBody TrainerDto trainerDto) {
    try {
      String username = profileService.generateUsername(trainerDto.getFirstName(),
          trainerDto.getLastName());
      String password = profileService.generateRandomPassword();

      TrainingType trainingType = trainerService.checkSpecializationCorrectness(
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
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }


  @GetMapping("/getTrainerWithTraineesList")
  public ResponseEntity<?> getTrainerProfileWithTraineeList(@RequestParam String username) {
    try {
      Trainer trainer = trainerService.getTrainerByUsername(username);

      List<Trainee> trainees = traineeService.getAllTraineesByTrainerUsername(username);

      List<TraineeDto> traineeDtosList = traineeMapper.toTraineeDto3List(trainees);

      TrainerWithTraineeListDto responseDto = trainerMapper.toTrainerWithTraineeListDto(trainer,
          traineeDtosList
      );

      return ResponseEntity.ok(responseDto);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while retrieving the profile");
    }
  }


  /*
  9. Update Trainer Profile (PUT method)
a. Request
I. Username (required)
II. First Name (required)
III. Last Name (required)
IV. Specialization (read only) (Training type reference)
V. Is Active (required)

b. Response
I. Username
II. First Name
III. Last Name
IV. Specialization (Training type reference)
V. Is Active
VI. Trainees List
1. Trainee Username
2. Trainee First Name
3. Trainee Last Name
   */


  @PutMapping("/updateTrainerWithTraineesList")
  public ResponseEntity<?> updateTrainerProfile(@RequestBody TrainerDto trainerDto) {

    try {

      Trainer trainer = trainerService.getTrainerByUsername(trainerDto.getUsername());

      System.out.println("THIS IS TRAINER FROM DB : " + trainer);

      List<Trainee> trainees = traineeService.getAllTraineesByTrainerUsername(
          trainer.getUsername());

      System.out.println("THIS ARE HIS TRAINEES: " + trainees);

      List<TraineeDto3fields> traineeDtosList = traineeMapper.toTraineeDto3ListNew(
          trainees); //works!

      System.out.println("THIS are TRAINEE_DTO LIST: " + traineeDtosList);

      //updating trainer from DTO
      Trainer updatedTrainerFromDto = trainerMapper.updateTrainerFromDto(trainerDto, trainer);
      trainerService.saveTrainer(updatedTrainerFromDto);

      System.out.println("UPDATED trainer is SAVED :  " + updatedTrainerFromDto);

      TrainerWithTraineeListDto responseDto = trainerMapper.toUpdatedTrainerWithTraineeListDtoNew(
          trainer, traineeDtosList);

      System.out.println("THIS IS RESPONSE_DTO : " + responseDto);

      return ResponseEntity.ok(responseDto);

    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while updating the profile");
    }
  }

    /*
13. Get Trainer Trainings List (GET method)
  a. Request
     I. Username (required)
     II. Period From (optional)
     III. Period To (optional)
     IV. Trainee Name (optional)
  b. Response
     I. Training Name
     II. Training Date
     III. Training Type
     IV. Training Duration
     V. Trainee Name
 */

  // Request example: http://localhost:8081/trainer/getTrainerTrainingsListByDynamicCriteria?username=Pol.Mark&periodFrom=2221-11-11&periodTo=2226-11-11&traineeFirstName=Roman

  // This method retrieves a trainer's training sessions with optional
  // filtering by date range and trainee's first name.
  @GetMapping("/getTrainerTrainingsListByDynamicCriteria")
  public ResponseEntity<?> getTrainerTrainingsListByDynamicCriteria(
      @RequestParam String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String traineeFirstName) {

    TrainerTrainingRequestDto trainerTrainingRequestDto
        = trainerMapper.toDto(username, periodFrom, periodTo, traineeFirstName);

    try {
      List<Training> trainings = trainingService.findTrainerTrainingsByUsername(username);


      if (periodFrom != null && periodTo != null) {
        trainings = trainings.stream()
            .filter(t -> !t.getTrainingDate().isBefore(trainerTrainingRequestDto.getPeriodFrom())
                && !t.getTrainingDate()
                .isAfter(trainerTrainingRequestDto.getPeriodTo()))
            .collect(Collectors.toList());
      }

      if (traineeFirstName != null) {
        trainings = trainings.stream()
            .filter(t -> t.getTrainee().getUser().getFirstName().equals(traineeFirstName))
            .collect(Collectors.toList());
      }

      List<TrainingForTrainerResponseDto> dtoList = TrainingForTrainerMapper.INSTANCE.toDtoList(trainings);


      return ResponseEntity.ok(dtoList);


    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while updating the profile");
    }
  }
}