package com.example.gymApp.dto.trainingType;

import com.example.gymApp.dto.training.TrainingForTrainerResponseDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
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
public class TrainingForTrainerMapperImpl implements TrainingForTrainerMapper {

    @Override
    public TrainingForTrainerResponseDto toDto(Training training) {
        if ( training == null ) {
            return null;
        }

        TrainingForTrainerResponseDto trainingForTrainerResponseDto = new TrainingForTrainerResponseDto();

        trainingForTrainerResponseDto.setTrainingName( training.getTrainingName() );
        trainingForTrainerResponseDto.setTrainingDate( training.getTrainingDate() );
        trainingForTrainerResponseDto.setTrainingType( trainingTrainingTypeName( training ) );
        trainingForTrainerResponseDto.setTraineeName( trainingTraineeUserFirstName( training ) );

        return trainingForTrainerResponseDto;
    }

    @Override
    public List<TrainingForTrainerResponseDto> toDtoList(List<Training> trainings) {
        if ( trainings == null ) {
            return null;
        }

        List<TrainingForTrainerResponseDto> list = new ArrayList<TrainingForTrainerResponseDto>( trainings.size() );
        for ( Training training : trainings ) {
            list.add( toDto( training ) );
        }

        return list;
    }

    private String trainingTrainingTypeName(Training training) {
        if ( training == null ) {
            return null;
        }
        TrainingType trainingType = training.getTrainingType();
        if ( trainingType == null ) {
            return null;
        }
        String name = trainingType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String trainingTraineeUserFirstName(Training training) {
        if ( training == null ) {
            return null;
        }
        Trainee trainee = training.getTrainee();
        if ( trainee == null ) {
            return null;
        }
        User user = trainee.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }
}
