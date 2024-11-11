package com.example.gymApp.dto.trainer;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeResponseDto;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    //Important!
  TrainerWithTraineeListDto toTrainerWithTraineeListDto(Trainer trainer,
      List<TraineeDto> traineeDto);

//  @Mapping(source = "username", target = "user.username")
//  @Mapping(source = "firstName", target = "user.firstName")
//  @Mapping(source = "lastName", target = "user.lastName")
//  @Mapping(source = "active", target = "user.active")
//  @Mapping(source = "trainingType", target = "specialization")
//  Trainer updateTrainerFromDto(TrainerDto trainerDto, @MappingTarget Trainer trainer);
//
//
//  @Mapping(source = "trainer.user.username", target = "username")
//  @Mapping(source = "trainer.user.firstName", target = "firstName")
//  @Mapping(source = "trainer.user.lastName", target = "lastName")
//  @Mapping(source = "trainer.specialization", target = "trainingType")
//  @Mapping(source = "trainer.user.active", target = "active")
//  @Mapping(source = "traineeDto", target = "traineeDtoList")
//  TrainerWithTraineeListDto toUpdatedTrainerWithTraineeListDto(Trainer trainer,
//      List<TraineeDto> traineeDto);

//3 fields


//  @Mapping(source = "trainer.user.username", target = "username")
//  @Mapping(source = "trainer.user.firstName", target = "firstName")
//  @Mapping(source = "trainer.user.lastName", target = "lastName")
//  @Mapping(source = "trainer.specialization", target = "trainingType")
//  @Mapping(source = "trainer.user.active", target = "active")
//  @Mapping(source = "traineeDto", target = "traineeDtoList")
//    //here traineeDto
//
//  TrainerWithTraineeListDto toUpdatedTrainerWithTraineeListDtoNew(Trainer trainer,
//      List<TraineeResponseDto> traineeDto);  // is here!
//




    @Mapping(source = "trainer.user.firstName", target = "firstName")
    @Mapping(source = "trainer.user.lastName", target = "lastName")
    @Mapping(source = "trainer.user.username", target = "username")
    @Mapping(source = "trainer.specialization", target = "trainingType")
    @Mapping(source = "trainer.user.active", target = "active")
    @Mapping(source = "trainees", target = "traineeDtoList")
    TrainerWithTraineeListDto toDto(Trainer trainer, List<Trainee> trainees);

//    List<TraineeDto> toDto( List<Trainee> trainees);  //todo try this!


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


  //13
  @Mapping(target = "username", source = "username")
  @Mapping(target = "periodFrom", source = "periodFrom")
  @Mapping(target = "periodTo", source = "periodTo")
  @Mapping(target = "traineeFirstName", source = "traineeFirstName")
  TrainerTrainingRequestDto toDto(
      String username,
      String periodFrom,  //notNeed?
      String periodTo,
      String traineeFirstName);
}



















