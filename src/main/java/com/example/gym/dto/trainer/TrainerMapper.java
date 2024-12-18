package com.example.gym.dto.trainer;

import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainingtype.TrainingForTraineeMapper;
import com.example.gym.model.Trainee;
import com.example.gym.model.Trainer;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TrainingForTraineeMapper.class})
public interface TrainerMapper {

  @Mapping(source = "firstName", target = "user.firstName")
  @Mapping(source = "lastName", target = "user.lastName")
  @Mapping(source = "specialization", target = "specialization", qualifiedByName = "mapToTrainingType")
  @Mapping(source = "username", target = "user.username")
  Trainer toTrainer(TrainerDto dto);


  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "specialization.name", target = "specialization")
  TrainerDto toTrainerDto(Trainer trainer);


  @Mapping(source = "trainer.user.firstName", target = "firstName")
  @Mapping(source = "trainer.user.lastName", target = "lastName")
  @Mapping(source = "trainer.specialization.name", target = "trainingType.name")
  @Mapping(source = "trainer.user.active", target = "active")
  @Mapping(source = "traineeDto", target = "traineeDtoList")
  TrainerWithTraineeListDto toTrainerWithTraineeListDto(Trainer trainer,
      List<TraineeDto> traineeDto);


  @Mapping(source = "trainer.user.firstName", target = "firstName")
  @Mapping(source = "trainer.user.lastName", target = "lastName")
  @Mapping(source = "trainer.user.username", target = "username")
  @Mapping(source = "trainer.specialization", target = "trainingType")
  @Mapping(source = "trainer.user.active", target = "active")
  @Mapping(source = "trainees", target = "traineeDtoList")
  TrainerWithTraineeListDto toDto(Trainer trainer, List<Trainee> trainees);


  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "user.username", target = "username")
  TrainerWithTraineeListDto.TraineeDto toTraineeDto(Trainee trainee);

  List<TrainerWithTraineeListDto.TraineeDto> mapTrainees(List<Trainee> trainees);


  @Mapping(source = "user.username", target = "username")
  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "specialization", target = "trainingType")
  TrainerResponseDto toTrainerResponseDto(Trainer trainer);

  List<TrainerResponseDto> toTrainerResponseDto(List<Trainer> trainers);


  @Mapping(target = "username", source = "username")
  @Mapping(target = "periodFrom", source = "periodFrom")
  @Mapping(target = "periodTo", source = "periodTo")
  @Mapping(target = "traineeFirstName", source = "traineeFirstName")
  TrainerTrainingRequestDto toDto(
      String username,
      String periodFrom,
      String periodTo,
      String traineeFirstName);
}



















