package com.example.gymApp.dto.trainer;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeDto3fields;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.model.Trainer;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

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

  @Mapping(source = "username", target = "user.username")
  @Mapping(source = "firstName", target = "user.firstName")
  @Mapping(source = "lastName", target = "user.lastName")
  @Mapping(source = "active", target = "user.active")
  @Mapping(source = "trainingType", target = "specialization")
  Trainer updateTrainerFromDto(TrainerDto trainerDto, @MappingTarget Trainer trainer);


  @Mapping(source = "trainer.user.username", target = "username")
  @Mapping(source = "trainer.user.firstName", target = "firstName")
  @Mapping(source = "trainer.user.lastName", target = "lastName")
  @Mapping(source = "trainer.specialization", target = "trainingType")
  @Mapping(source = "trainer.user.active", target = "active")
  @Mapping(source = "traineeDto", target = "traineeDtoList")
  TrainerWithTraineeListDto toUpdatedTrainerWithTraineeListDto(Trainer trainer,
      List<TraineeDto> traineeDto);

//3 fields


  @Mapping(source = "trainer.user.username", target = "username")
  @Mapping(source = "trainer.user.firstName", target = "firstName")
  @Mapping(source = "trainer.user.lastName", target = "lastName")
  @Mapping(source = "trainer.specialization", target = "trainingType")
  @Mapping(source = "trainer.user.active", target = "active")
  @Mapping(source = "traineeDto", target = "traineeDtoList")
    //here traineeDto

  TrainerWithTraineeListDto toUpdatedTrainerWithTraineeListDtoNew(Trainer trainer,
      List<TraineeDto3fields> traineeDto);  // is here!

//task 10

  TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

  @Mapping(source = "user.username", target = "username")
  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "specialization", target = "trainingType")
  TrainerDto4fields toTrainerDto4fields(Trainer trainer);


  List<TrainerDto4fields> toTrainerDto4fieldsList(List<Trainer> trainers);


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



















