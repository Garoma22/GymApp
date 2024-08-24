package com.example.gymApp.Dao;

import com.example.gymApp.Model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TraineeDAO {

    private final InMemoryStorage storage;

    @Autowired
    public TraineeDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void createTrainee(Trainee trainee) {
        storage.getTraineesMap().put(trainee.getId(), trainee);
    }

    public void updateTrainee(Trainee trainee) {
        storage.getTraineesMap().put(trainee.getId(), trainee);
    }

    public void deleteTrainee(Long id) {
        storage.getTraineesMap().remove(id);
    }

    public Trainee getTraineeById(Long id) {
        return storage.getTraineesMap().get(id);
    }

    public List<Trainee> getAllTrainees() {
        return List.copyOf(storage.getTraineesMap().values());
    }
}