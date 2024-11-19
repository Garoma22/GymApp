package com.example.gymApp.dto.trainingType;

import com.example.gymApp.dto.training.TrainingForTraineeResponseDto;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T09:39:28+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainingForTraineeMapperImpl implements TrainingForTraineeMapper {

    @Override
    public TrainingForTraineeResponseDto toDto(Training training) {
        if ( training == null ) {
            return null;
        }

        TrainingForTraineeResponseDto trainingForTraineeResponseDto = new TrainingForTraineeResponseDto();

        trainingForTraineeResponseDto.setTrainingName( training.getTrainingName() );
        trainingForTraineeResponseDto.setTrainingDate( training.getTrainingDate() );
        trainingForTraineeResponseDto.setTrainingType( trainingTrainingTypeName( training ) );
        trainingForTraineeResponseDto.setTrainingDuration( training.getTrainingDuration() );
        trainingForTraineeResponseDto.setTrainerName( trainingTrainerUserFirstName( training ) );

        return trainingForTraineeResponseDto;
    }

    @Override
    public List<TrainingForTraineeResponseDto> toDtoList(List<Training> trainings) {
        if ( trainings == null ) {
            return null;
        }

        List<TrainingForTraineeResponseDto> list = new ArrayList<TrainingForTraineeResponseDto>( trainings.size() );
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

    private String trainingTrainerUserFirstName(Training training) {
        if ( training == null ) {
            return null;
        }
        Trainer trainer = training.getTrainer();
        if ( trainer == null ) {
            return null;
        }
        User user = trainer.getUser();
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
