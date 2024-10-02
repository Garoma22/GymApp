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
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/protected/trainer-management")
@AllArgsConstructor
public class TrainerController {

  private static final Logger log = LoggerFactory.getLogger(TrainerController.class);
  private final TrainerService trainerService;
  private final ProfileService profileService;
  private final TrainerMapper trainerMapper;
  private final TraineeDto traineeDto;
  private final TraineeService traineeService;
  private final TraineeMapper traineeMapper;
  private final TrainingService trainingService;



  /*
  8. Get Trainer Profile (GET method)
a. Request
I. Username (required)
b. Response
I. First Name
II. Last Name
III. Specialization (Training type reference)
IV. Is Active
V. Trainees List
   1. Trainee Username
   2. Trainee First Name
   3. Trainee Last Name
   */

  @GetMapping("/trainers/{username}/trainees")
  public ResponseEntity<?> getTrainerProfileWithTraineeList(@PathVariable String username) {
    Trainer trainer = trainerService.getTrainerByUsername(username);

    List<Trainee> trainees = traineeService.getAllTraineesByTrainerUsername(username);

    List<TraineeDto> traineeDtosList = traineeMapper.toTraineeDto3List(trainees);

    TrainerWithTraineeListDto responseDto = trainerMapper.toTrainerWithTraineeListDto(trainer,
        traineeDtosList
    );

    return ResponseEntity.ok(responseDto);
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

//HERE WE UPDATING FIRST AND SECOND NAME + MAKE A CUSTOM RESPONSE
//  @PutMapping("/update-trainer-with-trainees-list")

  @PutMapping("/trainers/{username}/trainees")
  public ResponseEntity<?> updateTrainerProfile(@PathVariable String username,
      @RequestBody TrainerDto trainerDto) {

    Trainer trainer = trainerService.getTrainerByUsername(username);

    log.info("THIS IS TRAINER FROM DB : " + trainer);

    List<Trainee> trainees = traineeService.getAllTraineesByTrainerUsername(
        trainer.getUsername());

    log.info("THIS ARE HIS TRAINEES: " + trainees);

    List<TraineeDto3fields> traineeDtosList = traineeMapper.toTraineeDto3ListNew(
        trainees); //works!

    log.info("THIS are TRAINEE_DTO LIST: " + traineeDtosList);

    //updating trainer from DTO
    Trainer updatedTrainerFromDto = trainerMapper.updateTrainerFromDto(trainerDto, trainer);
    trainerService.saveTrainer(updatedTrainerFromDto);

    log.info("UPDATED trainer is SAVED :  " + updatedTrainerFromDto);

    TrainerWithTraineeListDto responseDto = trainerMapper.toUpdatedTrainerWithTraineeListDtoNew(
        trainer, traineeDtosList);

    log.info("THIS IS RESPONSE_DTO : " + responseDto);

    return ResponseEntity.ok(responseDto);
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

  /* This method retrieves a trainer's training sessions with optional
   filtering by date range and trainee's first name.

   */

//  @GetMapping("/trainer-trainings-list-by-dynamic-criteria")

  @GetMapping("/trainers/{username}/trainings")
  public ResponseEntity<?> getTrainerTrainingsListByDynamicCriteria(
      @PathVariable String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String traineeFirstName) {

    TrainerTrainingRequestDto trainerTrainingRequestDto
        = trainerMapper.toDto(username, periodFrom, periodTo, traineeFirstName);

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

      List<TrainingForTrainerResponseDto> dtoList = TrainingForTrainerMapper.INSTANCE.toDtoList(
          trainings);

      return ResponseEntity.ok(dtoList);
  }
}