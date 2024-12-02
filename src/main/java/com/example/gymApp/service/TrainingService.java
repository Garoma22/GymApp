package com.example.gymApp.service;

import com.example.gymApp.dto.trainee.TraineeTrainingRequestDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerTrainingRequestDto;
import com.example.gymApp.dto.training.TrainerWorkloadServiceDto;
import com.example.gymApp.dto.training.TrainingForTraineeResponseDto;
import com.example.gymApp.dto.training.TrainingForTrainerResponseDto;
import com.example.gymApp.dto.training.TrainingInfoResponseDto;
import com.example.gymApp.dto.training.TrainingInfoResponseDtoMapper;
import com.example.gymApp.dto.training.TrainingRequestDto;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.dto.trainingType.TrainingForTrainerMapper;
import com.example.gymApp.feign.TrainerWorkloadServiceFeign;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.TrainingTypeRepository;
import com.example.gymApp.repository.UserRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@Slf4j
@AllArgsConstructor
public class TrainingService {


  private final TraineeRepository traineeRepository;
  private final UserRepository userRepository;
  private final TrainerRepository trainerRepository;
  private final TrainingTypeRepository trainingTypeRepository;
  private final TrainingRepository trainingRepository;
  private final TrainingForTraineeMapper trainingForTraineeMapper;
  private final TrainingForTrainerMapper trainingForTrainerMapper;
  private final TrainingInfoResponseDtoMapper trainingInfoResponseDtoMapper;
  private final TrainerMapper trainerMapper;
  private final TrainerService trainerService;
  private final TrainingInfoResponseDto trainingInfoResponseDto;
  private final TrainerWorkloadServiceFeign trainerWorkloadServiceFeign;


  private static final String ADD = "ADD";
  private static final String DELETE = "DELETE";

  @Transactional
  public Training createTraining(String trainerUsername, String traineeUsername,
      String trainingName,
      LocalDate dateOfTraining, int durationInHours) {

    Trainer trainer = trainerRepository.findByUserUsername(trainerUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainer with username " + trainerUsername + " not found"));

    Trainee trainee = traineeRepository.findByUserUsername(traineeUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainee with username " + traineeUsername + " not found"));

    log.info(trainee.toString());

    if (!trainee.getUser().isActive() || !trainer.getUser().isActive()) {
      log.info("Training could not be created because of false status of trainee : " + trainee
          + " or trainer :" + trainer);
      throw new IllegalArgumentException(
          "Training could not be created due to inactive trainee or trainer.");
    }

    Training training = new Training();
    training.setTrainer(trainer);
    training.setTrainee(trainee);
    training.setTrainingType(trainer.getSpecialization());
    training.setTrainingName(trainingName);
    training.setTrainingDate(dateOfTraining);
    training.setTrainingDuration(durationInHours);

    trainingRepository.save(training);

    log.info("Training " + training + " successfully saved");



    createTrainerWorkloadServiceDto(trainerUsername, trainer, dateOfTraining, durationInHours);

    return training;
  }

  // entity to send to 2-nd service
  @CircuitBreaker(name = "trainerWorkloadService", fallbackMethod = "handleTrainingFallback")
  public void createTrainerWorkloadServiceDto(String trainerUsername, Trainer trainer,
      LocalDate dateOfTraining, int durationInHours) {
    TrainerWorkloadServiceDto dto = new TrainerWorkloadServiceDto();
    dto.setTrainerUsername(trainerUsername);
    dto.setTrainerFirstName(trainer.getUser().getFirstName());
    dto.setTrainerLastName(trainer.getUser().getLastName());
    dto.setActive(trainer.getUser().isActive());
    dto.setTrainingDate(dateOfTraining);
    dto.setTrainingDuration(durationInHours);
    dto.setActionType(ADD);

    try {
      trainerWorkloadServiceFeign.handleTraining(dto);
    } catch (FeignException e) {
      log.error("Error while sending training data to trainer-workload-service: {}", e.getMessage());
      log.error("Request Body: {}", dto);
      log.error("Response Status Code: {}", e.status());
      log.error("Response Body: {}", e.contentUTF8());
    }
  }
  public void handleTrainingFallback(TrainerWorkloadServiceDto dto, Throwable t) {
    log.error("Failed to handle training due to {}", t.getMessage());
  }


  @Transactional
  public void createTraining(List<Trainer> trainers, Trainee trainee) {

    for (Trainer trainer : trainers) {

      List<Training> existingTrainings = trainingRepository.findTrainings(trainer, trainee,
          LocalDate.parse("2222-11-11"));

      if (existingTrainings.isEmpty()) {

        //todo : add some logic for isActive status of users

        Training training = new Training();

        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainer.getSpecialization());
        training.setTrainingName(trainer.getUser().getUsername() + " - " + trainee.getUsername()
            + " test training with hardcode data");
        training.setTrainingDate(LocalDate.parse("2222-11-11")); //hardcode here!
        training.setTrainingDuration(1);  //hardcode!
        trainingRepository.save(training);
        System.out.println("New training is created: " + training);
      }
    }
  }



  public List<Training> getTrainingsByUserUsername(String traineeUsername, LocalDate startDate,
      LocalDate finishDate, String trainerName, String specialization) {

    return trainingRepository.getAllTrainingsByTraineeAndCriteria(traineeUsername, startDate,
        finishDate, trainerName, specialization);
  }

  public List<Training> getTraineesList(String trainerUsername, String traineeName,
      LocalDate startDate, LocalDate finishDate) {

    return trainingRepository.getAllTrainingsByTrainerAndCriteria(trainerUsername, traineeName,
        startDate, finishDate);
  }

  public List<Trainer> getAllTrainersByTraineeUsername(String traineeUsername) {
    return trainingRepository.getAllTrainersByTrainee(traineeUsername);
  }


  public List<Training> findTraineeTrainingsByUsername(String traineeUsername) {

    return trainingRepository.findTraineeTrainingsByUsername(traineeUsername);
  }

  public List<Training> findTrainerTrainingsByUsername(String trainerUsername) {
    return trainingRepository.findTrainerTrainingsByUsername(trainerUsername);
  }

  public List<TrainingForTraineeResponseDto> findTraineeTrainingsWithFilters(
      TraineeTrainingRequestDto requestDto) {
    List<Training> trainings = findTraineeTrainingsByUsername(requestDto.getUsername());

    if (requestDto.getPeriodFrom() != null && requestDto.getPeriodTo() != null) {
      trainings = trainings.stream()
          .filter(t -> !t.getTrainingDate().isBefore(requestDto.getPeriodFrom())
              && !t.getTrainingDate().isAfter(requestDto.getPeriodTo()))
          .collect(Collectors.toList());
    }

    if (requestDto.getTrainerName() != null) {
      trainings = trainings.stream()
          .filter(t -> t.getTrainer().getUser().getFirstName().equals(requestDto.getTrainerName()))
          .collect(Collectors.toList());
    }

    if (requestDto.getSpecialization() != null) {
      trainings = trainings.stream()
          .filter(t -> t.getTrainer().getSpecialization().getName()
              .equals(requestDto.getSpecialization()))
          .collect(Collectors.toList());
    }

    return trainingForTraineeMapper.toDtoList(
        trainings);


  }

  public List<TrainingForTrainerResponseDto> getTrainerTrainingsByCriteria(String username,
      String periodFrom, String periodTo, String traineeFirstName) {
    TrainerTrainingRequestDto trainerTrainingRequestDto
        = trainerMapper.toDto(username, periodFrom, periodTo, traineeFirstName);

    List<Training> trainings = findTrainerTrainingsByUsername(username);

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

    List<TrainingForTrainerResponseDto> dtoList = trainingForTrainerMapper.toDtoList(
        trainings);

    return dtoList;
  }

  public TrainingInfoResponseDto getTrainingInfoResponseDto(
      TrainingRequestDto request) {

        createTraining(request.getTrainerUsername(), request.getTraineeUsername(),
        request.getTrainingName(), request.getTrainingDate(), request.getTrainingDuration());

    return trainingInfoResponseDtoMapper.trainingToTrainingResponseDto(request);
  }
}








