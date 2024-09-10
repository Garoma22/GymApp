package com.example.gymApp.repository;

import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import java.time.LocalDate;
import java.util.List;
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


}




