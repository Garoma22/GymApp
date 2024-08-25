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


    public boolean updateTrainee(Trainee trainee) {
        if (storage.getTraineesMap().containsKey(trainee.getId())) {
            storage.getTraineesMap().put(trainee.getId(), trainee);
            return true; // Успешное обновление
        } else {
            return false; // Стажер с таким id не найден
        }
    }


    public boolean deleteTrainee(Long id) {
        if(storage.getTraineesMap().containsKey(id)){
            storage.getTraineesMap().remove(id);
            return true;

        }else{
          return false;
        }

    }

    public Trainee getTraineeById(Long id) {
        return storage.getTraineesMap().get(id);
    }

    public List<Trainee> getAllTrainees() {
        return List.copyOf(storage.getTraineesMap().values());
    }
}