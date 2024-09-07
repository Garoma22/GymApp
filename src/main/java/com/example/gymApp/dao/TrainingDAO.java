//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Training;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Repository
//public class TrainingDAO {
//
//    private final InMemoryStorage storage;
//
//    @Autowired
//    public TrainingDAO(InMemoryStorage storage) {
//        this.storage = storage;
//    }
//
//    public boolean createTraining(Training training) {
//        if (storage.getTrainingsMap().containsKey(training.getId())) {
//            throw new NoSuchElementException("Training with ID " + training.getId() + " already exists.");
//        }
//        storage.getTrainingsMap().put(training.getId(), training);
//        return true;
//    }
//
//    public boolean updateTraining(Training training) {
//        if (!storage.getTrainingsMap().containsKey(training.getId())) {
//            throw new NoSuchElementException("Training with ID " + training.getId() + " does not exist.");
//        }
//        storage.getTrainingsMap().put(training.getId(), training);
//        return true;
//    }
//
//    public boolean deleteTraining(Long id) {
//        if (!storage.getTrainingsMap().containsKey(id)) {
//            throw new NoSuchElementException("Training with ID " + id + " not found.");
//        }
//        storage.getTrainingsMap().remove(id);
//        return true;
//    }
//
//    public Training getTrainingById(Long id) {
//        Training training = storage.getTrainingsMap().get(id);
//        if (training == null) {
//            throw new NoSuchElementException("Training with ID " + id + " not found.");
//        }
//        return training;
//    }
//
//    public List<Training> getAllTrainings() {
//        return new ArrayList<>(storage.getTrainingsMap().values());
//    }
//}
