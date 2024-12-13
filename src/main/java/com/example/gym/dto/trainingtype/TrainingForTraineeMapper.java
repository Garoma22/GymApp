package com.example.gym.dto.trainingtype;


import com.example.gym.dto.training.TrainingForTraineeResponseDto;
import com.example.gym.model.Training;
import com.example.gym.model.TrainingType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TrainingForTraineeMapper {

  @Named("mapToTrainingType")
  default TrainingType mapToTrainingType(String specializationName) {
    TrainingType trainingType = new TrainingType();
    trainingType.setName(specializationName);
    return trainingType;
  }
  @Named("trainingTypeToMap")
  default String trainingTypeToMap (TrainingType trainingType){
    return trainingType.getName();
  }



  //12
//  TrainingForTraineeMapper INSTANCE = Mappers.getMapper(TrainingForTraineeMapper.class);
  @Mapping(source = "trainingName", target = "trainingName")
  @Mapping(source = "trainingDate", target = "trainingDate")
  @Mapping(source = "trainingType.name", target = "trainingType")
  @Mapping(source = "trainingDuration", target = "trainingDuration")
//  @Mapping(expression = "java(training.getTrainer().getUser().getFirstName() + ' ' + training.getTrainer().getUser().getLastName())", target = "trainerName")
  @Mapping(source = "trainer.user.firstName", target = "trainerName")
  TrainingForTraineeResponseDto toDto(Training training);

  List<TrainingForTraineeResponseDto> toDtoList(List<Training> trainings);





}






