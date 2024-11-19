package com.example.gymApp.dto.trainer;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T09:39:28+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TrainerMapperImpl implements TrainerMapper {

    @Autowired
    private TrainingForTraineeMapper trainingForTraineeMapper;

    @Override
    public Trainer toTrainer(TrainerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Trainer trainer = new Trainer();

        trainer.setUser( trainerDtoToUser( dto ) );
        trainer.setSpecialization( trainingForTraineeMapper.mapToTrainingType( dto.getSpecialization() ) );

        return trainer;
    }

    @Override
    public TrainerDto toTrainerDto(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }

        TrainerDto trainerDto = new TrainerDto();

        trainerDto.setFirstName( trainerUserFirstName( trainer ) );
        trainerDto.setLastName( trainerUserLastName( trainer ) );
        trainerDto.setSpecialization( trainerSpecializationName( trainer ) );

        return trainerDto;
    }

    @Override
    public TrainerWithTraineeListDto toTrainerWithTraineeListDto(Trainer trainer, List<TraineeDto> traineeDto) {
        if ( trainer == null && traineeDto == null ) {
            return null;
        }

        TrainerWithTraineeListDto trainerWithTraineeListDto = new TrainerWithTraineeListDto();

        if ( trainer != null ) {
            trainerWithTraineeListDto.setTrainingType( trainingTypeToTrainingType( trainer.getSpecialization() ) );
            trainerWithTraineeListDto.setFirstName( trainerUserFirstName( trainer ) );
            trainerWithTraineeListDto.setLastName( trainerUserLastName( trainer ) );
            trainerWithTraineeListDto.setActive( trainerUserActive( trainer ) );
        }
        trainerWithTraineeListDto.setTraineeDtoList( traineeDtoListToTraineeDtoList( traineeDto ) );

        return trainerWithTraineeListDto;
    }

    @Override
    public TrainerWithTraineeListDto toDto(Trainer trainer, List<Trainee> trainees) {
        if ( trainer == null && trainees == null ) {
            return null;
        }

        TrainerWithTraineeListDto trainerWithTraineeListDto = new TrainerWithTraineeListDto();

        if ( trainer != null ) {
            trainerWithTraineeListDto.setFirstName( trainerUserFirstName( trainer ) );
            trainerWithTraineeListDto.setLastName( trainerUserLastName( trainer ) );
            trainerWithTraineeListDto.setUsername( trainerUserUsername( trainer ) );
            trainerWithTraineeListDto.setTrainingType( trainer.getSpecialization() );
            trainerWithTraineeListDto.setActive( trainerUserActive( trainer ) );
        }
        trainerWithTraineeListDto.setTraineeDtoList( mapTrainees( trainees ) );

        return trainerWithTraineeListDto;
    }

    @Override
    public TrainerWithTraineeListDto.TraineeDto toTraineeDto(Trainee trainee) {
        if ( trainee == null ) {
            return null;
        }

        TrainerWithTraineeListDto.TraineeDto traineeDto = new TrainerWithTraineeListDto.TraineeDto();

        traineeDto.setFirstName( traineeUserFirstName( trainee ) );
        traineeDto.setLastName( traineeUserLastName( trainee ) );
        traineeDto.setUsername( traineeUserUsername( trainee ) );

        return traineeDto;
    }

    @Override
    public List<TrainerWithTraineeListDto.TraineeDto> mapTrainees(List<Trainee> trainees) {
        if ( trainees == null ) {
            return null;
        }

        List<TrainerWithTraineeListDto.TraineeDto> list = new ArrayList<TrainerWithTraineeListDto.TraineeDto>( trainees.size() );
        for ( Trainee trainee : trainees ) {
            list.add( toTraineeDto( trainee ) );
        }

        return list;
    }

    @Override
    public TrainerResponseDto toTrainerResponseDto(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }

        TrainerResponseDto trainerResponseDto = new TrainerResponseDto();

        trainerResponseDto.setUsername( trainerUserUsername( trainer ) );
        trainerResponseDto.setFirstName( trainerUserFirstName( trainer ) );
        trainerResponseDto.setLastName( trainerUserLastName( trainer ) );
        trainerResponseDto.setTrainingType( trainer.getSpecialization() );

        return trainerResponseDto;
    }

    @Override
    public List<TrainerResponseDto> toTrainerResponseDto(List<Trainer> trainers) {
        if ( trainers == null ) {
            return null;
        }

        List<TrainerResponseDto> list = new ArrayList<TrainerResponseDto>( trainers.size() );
        for ( Trainer trainer : trainers ) {
            list.add( toTrainerResponseDto( trainer ) );
        }

        return list;
    }

    @Override
    public TrainerTrainingRequestDto toDto(String username, String periodFrom, String periodTo, String traineeFirstName) {
        if ( username == null && periodFrom == null && periodTo == null && traineeFirstName == null ) {
            return null;
        }

        TrainerTrainingRequestDto trainerTrainingRequestDto = new TrainerTrainingRequestDto();

        trainerTrainingRequestDto.setUsername( username );
        if ( periodFrom != null ) {
            trainerTrainingRequestDto.setPeriodFrom( LocalDate.parse( periodFrom ) );
        }
        if ( periodTo != null ) {
            trainerTrainingRequestDto.setPeriodTo( LocalDate.parse( periodTo ) );
        }
        trainerTrainingRequestDto.setTraineeFirstName( traineeFirstName );

        return trainerTrainingRequestDto;
    }

    protected User trainerDtoToUser(TrainerDto trainerDto) {
        if ( trainerDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( trainerDto.getFirstName() );
        user.lastName( trainerDto.getLastName() );
        user.username( trainerDto.getUsername() );

        return user.build();
    }

    private String trainerUserFirstName(Trainer trainer) {
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

    private String trainerUserLastName(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }
        User user = trainer.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String trainerSpecializationName(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }
        TrainingType specialization = trainer.getSpecialization();
        if ( specialization == null ) {
            return null;
        }
        String name = specialization.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected TrainingType trainingTypeToTrainingType(TrainingType trainingType) {
        if ( trainingType == null ) {
            return null;
        }

        TrainingType trainingType1 = new TrainingType();

        trainingType1.setName( trainingType.getName() );
        trainingType1.setId( trainingType.getId() );

        return trainingType1;
    }

    private boolean trainerUserActive(Trainer trainer) {
        if ( trainer == null ) {
            return false;
        }
        User user = trainer.getUser();
        if ( user == null ) {
            return false;
        }
        boolean active = user.isActive();
        return active;
    }

    protected TrainerWithTraineeListDto.TraineeDto traineeDtoToTraineeDto(TraineeDto traineeDto) {
        if ( traineeDto == null ) {
            return null;
        }

        TrainerWithTraineeListDto.TraineeDto traineeDto1 = new TrainerWithTraineeListDto.TraineeDto();

        traineeDto1.setFirstName( traineeDto.getFirstName() );
        traineeDto1.setLastName( traineeDto.getLastName() );
        traineeDto1.setUsername( traineeDto.getUsername() );

        return traineeDto1;
    }

    protected List<TrainerWithTraineeListDto.TraineeDto> traineeDtoListToTraineeDtoList(List<TraineeDto> list) {
        if ( list == null ) {
            return null;
        }

        List<TrainerWithTraineeListDto.TraineeDto> list1 = new ArrayList<TrainerWithTraineeListDto.TraineeDto>( list.size() );
        for ( TraineeDto traineeDto : list ) {
            list1.add( traineeDtoToTraineeDto( traineeDto ) );
        }

        return list1;
    }

    private String trainerUserUsername(Trainer trainer) {
        if ( trainer == null ) {
            return null;
        }
        User user = trainer.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String traineeUserFirstName(Trainee trainee) {
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

    private String traineeUserLastName(Trainee trainee) {
        if ( trainee == null ) {
            return null;
        }
        User user = trainee.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String traineeUserUsername(Trainee trainee) {
        if ( trainee == null ) {
            return null;
        }
        User user = trainee.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
