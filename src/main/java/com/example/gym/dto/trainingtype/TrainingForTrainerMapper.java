package com.example.gym.dto.trainingtype;


import com.example.gym.dto.training.TrainingForTrainerResponseDto;
import com.example.gym.model.Training;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//13

@Mapper(componentModel = "spring")
public interface TrainingForTrainerMapper {



  @Mapping(source = "trainingName", target = "trainingName")
  @Mapping(source = "trainingDate", target = "trainingDate")
  @Mapping(source = "trainingType.name", target = "trainingType")
//  @Mapping(expression = "java(training.getTrainee().getUser().getFirstName() + ' ' + training.getTrainee().getUser().getLastName())", target = "traineeName")
  @Mapping(source = "trainee.user.firstName", target = "traineeName")

  TrainingForTrainerResponseDto toDto(Training training);

  List<TrainingForTrainerResponseDto> toDtoList(List<Training> trainings);
}



