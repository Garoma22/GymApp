package com.example.gymApp.Dao;


import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TrainerDAO {

    private final InMemoryStorage storage;

    @Autowired
    public TrainerDAO(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void createTrainer(Trainer trainer) {
        storage.getTrainersMap().put(trainer.getId(), trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        if(storage.getTrainersMap().containsKey(trainer.getId())){
            storage.getTrainersMap().put(trainer.getId(), trainer);
            return true;
        }else{
            return false;
        }
    }





    public boolean deleteTrainer(Long id) {
        if(storage.getTrainersMap().containsKey(id)){
            storage.getTrainersMap().remove(id);
            return true;

        }else{
            return false;
        }

    }

    public Trainer getTrainerById(Long id) {
        return storage.getTrainersMap().get(id);
    }

    public List<Trainer> getAllTrainers() {
        return List.copyOf(storage.getTrainersMap().values());
    }
}
