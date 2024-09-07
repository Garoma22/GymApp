////package com.example.gymApp.dao;
////
////import com.example.gymApp.model.Trainer;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Repository;
////
////import java.util.List;
////
////
////@Repository
////public class TrainerDAO {
////
////  private final InMemoryStorage storage;
////
////  @Autowired
////  public TrainerDAO(InMemoryStorage storage) {
////    this.storage = storage;
////  }
////
////  public boolean createTrainer(Trainer trainer) {
////    System.out.println("Это созданный тренер " + trainer);
////    storage.getTrainersRepository().put(trainer.getId(), trainer);
////    return storage.getTrainersRepository().containsKey(trainer.getId());
////  }
////
////  public boolean updateTrainer(Trainer trainer) {
////
////    storage.getTrainersRepository().put(trainer.getId(), trainer);
////    return storage.getTrainersRepository().containsKey(trainer.getId());
////  }
////
////  public boolean deleteTrainer(Long id) {
////    if (storage.getTrainersRepository().containsKey(id)) {
////      storage.getTrainersRepository().remove(id);
////      return true;
////
////    } else {
////      return false;
////    }
////
////  }
////
////  public Trainer getTrainerById(Long id) {
////    return storage.getTrainersRepository().get(id);
////  }
////
////  public List<Trainer> getAllTrainers() {
////    return List.copyOf(storage.getTrainersRepository().values());
////  }
////}
//
//
//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Trainer;
//import com.example.gymApp.repository.TrainerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Repository
//public class TrainerDAO {
//
//  private final TrainerRepository trainerRepository;
//
//  @Autowired
//  public TrainerDAO(TrainerRepository trainerRepository) {
//    this.trainerRepository = trainerRepository;
//  }
//
//  public boolean createTrainer(Trainer trainer) {
//    trainerRepository.save(trainer);
//    return trainerRepository.existsById(trainer.getId());
//  }
//
//  public boolean updateTrainer(Trainer trainer) {
//    if (!trainerRepository.existsById(trainer.getId())) {
//      throw new NoSuchElementException("Trainer with ID " + trainer.getId() + " not found.");
//    }
//    trainerRepository.save(trainer);
//    return true;
//  }
//
//  public boolean deleteTrainer(Long id) {
//    if (trainerRepository.existsById(id)) {
//      trainerRepository.deleteById(id);
//      return true;
//    }
//    return false;
//  }
//
//  public Trainer getTrainerById(Long id) {
//    return trainerRepository.findById(id).orElseThrow(() ->
//        new NoSuchElementException("Trainer with ID " + id + " not found."));
//  }
//
//  public List<Trainer> getAllTrainers() {
//    return trainerRepository.findAll();
//  }
//}
