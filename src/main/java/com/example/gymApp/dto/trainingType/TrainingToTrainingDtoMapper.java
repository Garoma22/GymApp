package com.example.gymApp.dto.trainingType;

import com.example.gymApp.dto.training.TrainingRequestDto;
import com.example.gymApp.dto.training.TrainingResponseDto;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TrainingToTrainingDtoMapper {
  TrainingResponseDto toDto(TrainingType trainingType);

  List<TrainingResponseDto> toTrainngDtoList(List<TrainingType> trainingTypes);


}
