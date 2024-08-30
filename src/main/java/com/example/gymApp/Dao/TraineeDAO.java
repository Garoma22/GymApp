package com.example.gymApp.Dao;

import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Service.ProfileService;
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

    public void createTrainee(Trainee trainee) {

        String generatedUsername = ProfileService.generateUsername(trainee.getFirstName(),trainee.getLastName());
        trainee.setUsername(generatedUsername);

        String generatedPassword = ProfileService.generateRandomPassword();
        trainee.setPassword(generatedPassword);

        storage.getTraineesMap().put(trainee.getId(), trainee);
    }

    public boolean updateTrainee(Trainee trainee) {
        if (storage.getTraineesMap().containsKey(trainee.getId())) {
            storage.getTraineesMap().put(trainee.getId(), trainee);
            return true;
        } else {
            return false;
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
        return new ArrayList<>(storage.getTraineesMap().values());
    }
}