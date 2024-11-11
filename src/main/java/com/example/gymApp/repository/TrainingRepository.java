package com.example.gymApp.repository;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {


  @Query("SELECT t FROM Training t WHERE t.trainee.user.username = :traineeUsername "
      + "AND t.trainer.user.firstName = :trainerName "
      + "AND t.trainer.specialization.name = :specialization "
      + "AND t.trainingDate BETWEEN :startDate AND :finishDate")
  List<Training> getAllTrainingsByTraineeAndCriteria(
      @Param("traineeUsername") String traineeUsername,
      @Param("startDate") LocalDate startDate,
      @Param("finishDate") LocalDate finishDate,
      @Param("trainerName") String trainerName,
      @Param("specialization") String specialization);


  @Query("SELECT t FROM Training t WHERE t.trainer.user.username = :trainerUsername "
      + "AND t.trainee.user.firstName = :traineeName "
      + "AND t.trainingDate BETWEEN :startDate AND :finishDate")
  List<Training> getAllTrainingsByTrainerAndCriteria(
      @Param("trainerUsername") String trainerUsername,
      @Param("traineeName") String traineeName,
      @Param("startDate") LocalDate startDate,
      @Param("finishDate") LocalDate finishDate);


  @Query("SELECT DISTINCT t.trainer FROM Training t WHERE t.trainee = :trainee")
  Set<Trainer> findDistinctTrainersByTrainee(@Param("trainee") Trainee trainee);

  List<Training> findByTrainee(Trainee trainee);


  @Query("SELECT DISTINCT t.trainee FROM Training t WHERE t.trainer = :trainer")
  Set<Trainee> findDistinctTraineeByTrainer(@Param("trainer") Trainer trainer);




  @Query("SELECT DISTINCT tr FROM Trainer tr WHERE tr.user.isActive = true "
      + "AND tr NOT IN (SELECT t.trainer FROM Training t WHERE t.trainee.user.username = :username)")
  List<Trainer> findAllActiveTrainersNotAssignedToTrainee(
      @Param("username") String traineeUsername);

  @Query("SELECT DISTINCT t.trainer FROM Training t WHERE t.trainee.user.username = :traineeUsername")
  List<Trainer> getAllTrainersByTrainee(@Param("traineeUsername") String traineeUsername);

  @Query("SELECT t FROM Training t WHERE t.trainer = :trainer AND t.trainee = :trainee AND t.trainingDate = :trainingDate")
  List<Training> findTrainings(@Param("trainer") Trainer trainer,
      @Param("trainee") Trainee trainee,
      @Param("trainingDate") LocalDate trainingDate);


//12
@Query("SELECT t FROM Training t JOIN t.trainee tr JOIN tr.user u WHERE u.username = :traineeUsername")
  List<Training> findTraineeTrainingsByUsername(@Param("traineeUsername") String traineeUsername);



@Query("SELECT t FROM Training t JOIN t.trainer tr JOIN tr.user u WHERE u.username = :trainerUsername")
List<Training> findTrainerTrainingsByUsername(@Param("trainerUsername") String trainerUsername);
}






