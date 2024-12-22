package com.example.gym.dto.trainee;

import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.model.Trainee;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface TraineeMapper {

  @Mapping(source = "firstName", target = "user.firstName")
  @Mapping(source = "lastName", target = "user.lastName")
  @Mapping(source = "dateOfBirth", target = "dateOfBirth")
  @Mapping(source = "address", target = "address")
  Trainee toTrainee(TraineeDto dto);

  @Named("toTraineeDto")
  @Mapping(target = "isActive", ignore = true)


  default TraineeDto toTraineeDto(Trainee trainee) {
    TraineeDto dto = new TraineeDto();
    dto.setFirstName(trainee.getUser().getFirstName());
    dto.setLastName(trainee.getUser().getLastName());
    dto.setUsername(trainee.getUser().getUsername());

    return dto;
  }


  @IterableMapping(qualifiedByName = "toTraineeDto")
  List<TraineeDto> toTraineeDtoList(List<Trainee> trainees);


  @Mapping(target = "username", source = "username")
  @Mapping(target = "periodFrom", source = "periodFrom")
  @Mapping(target = "periodTo", source = "periodTo")
  @Mapping(target = "trainerName", source = "trainerName")
  @Mapping(target = "specialization", source = "specializationName")
  TraineeTrainingRequestDto toDto(
      String username,
      String periodFrom,
      String periodTo,
      String trainerName,
      String specializationName);


  @Mapping(target = "username", source = "trainee.user.username")
  @Mapping(target = "firstName", source = "trainee.user.firstName")
  @Mapping(target = "lastName", source = "trainee.user.lastName")
  @Mapping(target = "dateOfBirth", source = "trainee.dateOfBirth")
  @Mapping(target = "address", source = "trainee.address")
  @Mapping(target = "active", source = "trainee.user.active")
  TraineeWithTrainerListDto toTraineeWithTrainerListDto(Trainee trainee, List<TrainerDto> trainersList);



}











