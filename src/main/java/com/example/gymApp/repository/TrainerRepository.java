package com.example.gymApp.repository;

import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

  Optional<Trainer> findByUserUsername(String username);

  Optional<Object> findByUser(User user);


  @Query("SELECT tr FROM Trainer tr WHERE tr.id NOT IN "
      + "(SELECT t.trainer.id FROM Training t WHERE t.trainee.user.username = :traineeUsername)")
  List<Trainer> getAllTrainersNotAssignedToTrainee(@Param("traineeUsername") String traineeUsername);



  List<Trainer> findByUserUsernameIn(List<String> usernames);


}