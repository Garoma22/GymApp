package com.example.gymApp.dao;

import com.example.gymApp.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class TraineeDAO {

  private final InMemoryStorage storage;

  @Autowired
  public TraineeDAO(InMemoryStorage storage) {
    this.storage = storage;
  }


  public boolean createTrainee(Trainee trainee) {
    storage.getTraineesMap().put(trainee.getId(), trainee);
    return storage.getTraineesMap().containsKey(trainee.getId());

  }

  public boolean updateTrainee(Trainee trainee) {
    return storage.getTraineesMap().put(trainee.getId(), trainee) != null;
  }

  public boolean deleteTrainee(Long id) {
    return storage.getTraineesMap().remove(id) != null;

  }

  public Trainee getTraineeById(Long id) {
    return storage.getTraineesMap().get(id);
  }

  public List<Trainee> getAllTrainees() {
    return new ArrayList<>(storage.getTraineesMap().values());
  }
}