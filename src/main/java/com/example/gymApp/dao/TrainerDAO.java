//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Trainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//
//@Repository
//public class TrainerDAO {
//
//  private final InMemoryStorage storage;
//
//  @Autowired
//  public TrainerDAO(InMemoryStorage storage) {
//    this.storage = storage;
//  }
//
//  public boolean createTrainer(Trainer trainer) {
//    System.out.println("Это созданный тренер " + trainer);
//    storage.getTrainersMap().put(trainer.getId(), trainer);
//    return storage.getTrainersMap().containsKey(trainer.getId());
//  }
//
//  public boolean updateTrainer(Trainer trainer) {
//
//    storage.getTrainersMap().put(trainer.getId(), trainer);
//    return storage.getTrainersMap().containsKey(trainer.getId());
//  }
//
//  public boolean deleteTrainer(Long id) {
//    if (storage.getTrainersMap().containsKey(id)) {
//      storage.getTrainersMap().remove(id);
//      return true;
//
//    } else {
//      return false;
//    }
//
//  }
//
//  public Trainer getTrainerById(Long id) {
//    return storage.getTrainersMap().get(id);
//  }
//
//  public List<Trainer> getAllTrainers() {
//    return List.copyOf(storage.getTrainersMap().values());
//  }
//}
