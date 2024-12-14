package com.example.gym.controller;

import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeMapper;
import com.example.gym.dto.trainee.TraineeTrainingRequestDto;
import com.example.gym.dto.trainee.TraineeWithTrainerListDto;


import com.example.gym.dto.trainer.TrainerResponseDto;
import com.example.gym.dto.training.TrainingForTraineeResponseDto;

import com.example.gym.service.TraineeService;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/protected/trainee-management")

public class TraineeController {

  private final TraineeService traineeService;
  private final TraineeMapper traineeMapper;
  private final TrainerService trainerService;
  private final TrainingService trainingService;


  @GetMapping("/trainees/{username}/trainers")
  public ResponseEntity<TraineeWithTrainerListDto> getTraineeProfileWithTrainersList(
      @PathVariable String username) {
    TraineeWithTrainerListDto responseDTO = traineeService.getTraineeProfileWithTrainersList(
        username);
    return ResponseEntity.ok(responseDTO);
  }


  @PutMapping("/trainees/{username}")
  public ResponseEntity<TraineeWithTrainerListDto> updateTraineeProfile(
      @RequestBody TraineeDto traineeDto,
      @PathVariable String username) {
    return ResponseEntity.ok(traineeService.updateTraineeProfile(traineeDto, username));
  }


  @DeleteMapping("/trainees/{username}")
  public ResponseEntity<String> deleteTraineeProfile(@PathVariable String username) {
    traineeService.deleteTraineeByUsername(username);
    return ResponseEntity.noContent().build();
  }


  @GetMapping("/trainees/{traineeUsername}/trainers/not-assigned")
  public ResponseEntity<List<TrainerResponseDto>> getNotAssignedActiveTrainersOnTrainee(
      @PathVariable String traineeUsername) {

    List<TrainerResponseDto> trainers = trainerService.getAllActiveTrainersNotAssignedToTrainee(
        traineeUsername);
    return ResponseEntity.ok(trainers);
  }

  @PutMapping("/trainees/{traineeUsername}/trainers")
  public ResponseEntity<List<TrainerResponseDto>> updateTraineeTrainersList(
      @PathVariable String traineeUsername, @RequestBody List<String> newTrainersUsernames) {
    List<TrainerResponseDto> updatedTrainersList = traineeService.updateTraineeTrainers(
        traineeUsername, newTrainersUsernames);
    return ResponseEntity.ok(updatedTrainersList);
  }


  @GetMapping("/trainees/{username}/trainings")
  public ResponseEntity<List<TrainingForTraineeResponseDto>> getTraineeTrainingsListByDynamicCriteria(
      @PathVariable String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String trainerFirstName,
      @RequestParam(required = false) String specializationName
  ) {
    TraineeTrainingRequestDto traineeTrainingRequestDto = traineeMapper.toDto(
        username, periodFrom, periodTo, trainerFirstName, specializationName
    );
    List<TrainingForTraineeResponseDto> responseDtos = trainingService
        .findTraineeTrainingsWithFilters(traineeTrainingRequestDto);

    return ResponseEntity.ok(responseDtos);
  }
}






