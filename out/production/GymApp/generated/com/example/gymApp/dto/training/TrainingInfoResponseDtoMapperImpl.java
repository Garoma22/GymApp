package com.example.gymApp.dto.training;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T09:39:29+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainingInfoResponseDtoMapperImpl implements TrainingInfoResponseDtoMapper {

    @Override
    public TrainingInfoResponseDto trainingToTrainingResponseDto(TrainingRequestDto trainingRequestDto) {
        if ( trainingRequestDto == null ) {
            return null;
        }

        TrainingInfoResponseDto trainingInfoResponseDto = new TrainingInfoResponseDto();

        trainingInfoResponseDto.setTrainingName( trainingRequestDto.getTrainingName() );
        trainingInfoResponseDto.setTrainingDate( trainingRequestDto.getTrainingDate() );
        trainingInfoResponseDto.setTrainingDuration( trainingRequestDto.getTrainingDuration() );

        return trainingInfoResponseDto;
    }
}
