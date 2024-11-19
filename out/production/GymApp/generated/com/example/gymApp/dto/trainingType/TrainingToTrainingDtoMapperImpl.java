package com.example.gymApp.dto.trainingType;

import com.example.gymApp.dto.training.TrainingResponseDto;
import com.example.gymApp.model.TrainingType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T09:39:29+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainingToTrainingDtoMapperImpl implements TrainingToTrainingDtoMapper {

    @Override
    public TrainingResponseDto toDto(TrainingType trainingType) {
        if ( trainingType == null ) {
            return null;
        }

        TrainingResponseDto trainingResponseDto = new TrainingResponseDto();

        trainingResponseDto.setId( trainingType.getId() );
        trainingResponseDto.setName( trainingType.getName() );

        return trainingResponseDto;
    }

    @Override
    public List<TrainingResponseDto> toTrainngDtoList(List<TrainingType> trainingTypes) {
        if ( trainingTypes == null ) {
            return null;
        }

        List<TrainingResponseDto> list = new ArrayList<TrainingResponseDto>( trainingTypes.size() );
        for ( TrainingType trainingType : trainingTypes ) {
            list.add( toDto( trainingType ) );
        }

        return list;
    }
}
