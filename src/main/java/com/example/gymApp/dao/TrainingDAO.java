////package com.example.gymApp.dao;
////
////import com.example.gymApp.model.Training;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Repository;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.NoSuchElementException;
////
////@Repository
////public class TrainingDAO {
////
////    private final InMemoryStorage storage;
////
////    @Autowired
////    public TrainingDAO(InMemoryStorage storage) {
////        this.storage = storage;
////    }
////
////    public boolean createTraining(Training training) {
////        if (storage.getTrainingsRepository().containsKey(training.getId())) {
////            throw new NoSuchElementException("Training with ID " + training.getId() + " already exists.");
////        }
////        storage.getTrainingsRepository().put(training.getId(), training);
////        return true;
////    }
////
////    public boolean updateTraining(Training training) {
////        if (!storage.getTrainingsRepository().containsKey(training.getId())) {
////            throw new NoSuchElementException("Training with ID " + training.getId() + " does not exist.");
////        }
////        storage.getTrainingsRepository().put(training.getId(), training);
////        return true;
////    }
////
////    public boolean deleteTraining(Long id) {
////        if (!storage.getTrainingsRepository().containsKey(id)) {
////            throw new NoSuchElementException("Training with ID " + id + " not found.");
////        }
////        storage.getTrainingsRepository().remove(id);
////        return true;
////    }
////
////    public Training getTrainingById(Long id) {
////        Training training = storage.getTrainingsRepository().get(id);
////        if (training == null) {
////            throw new NoSuchElementException("Training with ID " + id + " not found.");
////        }
////        return training;
////    }
////
////    public List<Training> getAllTrainings() {
////        return new ArrayList<>(storage.getTrainingsRepository().values());
////    }
////}
//
//
//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Training;
//import com.example.gymApp.repository.TrainingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Repository
//public class TrainingDAO {
//
//    private final TrainingRepository trainingRepository;
//
//    @Autowired
//    public TrainingDAO(TrainingRepository trainingRepository) {
//        this.trainingRepository = trainingRepository;
//    }
//
//    public boolean createTraining(Training training) {
//        if (trainingRepository.existsById(training.getId())) {
//            throw new NoSuchElementException("Training with ID " + training.getId() + " already exists.");
//        }
//        trainingRepository.save(training);
//        return true;
//    }
//
//    public boolean updateTraining(Training training) {
//        if (!trainingRepository.existsById(training.getId())) {
//            throw new NoSuchElementException("Training with ID " + training.getId() + " does not exist.");
//        }
//        trainingRepository.save(training);
//        return true;
//    }
//
//    public boolean deleteTraining(Long id) {
//        if (!trainingRepository.existsById(id)) {
//            throw new NoSuchElementException("Training with ID " + id + " not found.");
//        }
//        trainingRepository.deleteById(id);
//        return true;
//    }
//
//    public Training getTrainingById(Long id) {
//        return trainingRepository.findById(id)
//            .orElseThrow(() -> new NoSuchElementException("Training with ID " + id + " not found."));
//    }
//
//    public List<Training> getAllTrainings() {
//        return trainingRepository.findAll();
//    }
//}
