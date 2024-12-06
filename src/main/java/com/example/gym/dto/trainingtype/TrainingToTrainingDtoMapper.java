package com.example.gym.dto.trainingtype;

import com.example.gym.dto.training.TrainingResponseDto;
import com.example.gym.model.TrainingType;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TrainingToTrainingDtoMapper {
  TrainingResponseDto toDto(TrainingType trainingType);

  List<TrainingResponseDto> toTrainngDtoList(List<TrainingType> trainingTypes);


}
