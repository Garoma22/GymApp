package com.example.gymApp.Dao;

import com.example.gymApp.Model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDAO {

    private final InMemoryStorage storage;

    @Autowired
    public TrainingDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void createTraining(Training training) {
        storage.getTrainingsMap().put(training.getId(), training);
    }

    public void updateTraining(Training training) {
        storage.getTrainingsMap().put(training.getId(), training);
    }

    public void deleteTraining(Long id) {
        storage.getTrainingsMap().remove(id);
    }

    public Training getTrainingById(Long id) {
        return storage.getTrainingsMap().get(id);
    }

    public List<Training> getAllTrainings() {
        return new ArrayList<>(storage.getTrainingsMap().values());
    }

}