package com.example.gym.dto.training;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainingInfoResponseDtoMapper {

    @Mapping(source = "trainingName", target = "trainingName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "trainingDuration", target = "trainingDuration")
    TrainingInfoResponseDto trainingToTrainingResponseDto(TrainingRequestDto trainingRequestDto);
  }




