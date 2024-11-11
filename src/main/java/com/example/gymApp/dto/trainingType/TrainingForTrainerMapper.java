package com.example.gymApp.dto.trainingType;


import com.example.gymApp.dto.training.TrainingForTrainerResponseDto;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


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



