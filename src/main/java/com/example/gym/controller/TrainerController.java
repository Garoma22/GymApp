package com.example.gym.controller;

import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.dto.trainer.TrainerWithTraineeListDto;
import com.example.gym.dto.training.TrainingForTrainerResponseDto;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final TrainerService trainerService;
  private final TrainingService trainingService;


  @GetMapping("/trainers/{username}/trainees")
  public ResponseEntity<TrainerWithTraineeListDto> getTrainerProfileWithTraineeList(
      @PathVariable String username) {
    return ResponseEntity.ok(trainerService.getTrainerWithTrainees(username));
  }


  @PutMapping("trainers/{username}/trainees")
  public ResponseEntity<TrainerWithTraineeListDto> updateTrainerProfile(
      @PathVariable String username,
      @RequestBody TrainerDto trainerDto) {
    var responseDto = trainerService.updateTrainerProfile(username, trainerDto);
    log.info("THIS IS RESPONSE_DTO : " + responseDto);
    return ResponseEntity.ok(responseDto);
  }


  @GetMapping("/trainers/{username}/trainings")
  public ResponseEntity<List<TrainingForTrainerResponseDto>> getTrainerTrainingsListByDynamicCriteria(
      @PathVariable String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String traineeFirstName) {

    List<TrainingForTrainerResponseDto> dtoList = trainingService
        .getTrainerTrainingsByCriteria(username, periodFrom, periodTo, traineeFirstName);

    return ResponseEntity.ok(dtoList);
  }
}