package com.example.gymApp.Service;

import com.example.gymApp.Dao.TrainerDAO;
import com.example.gymApp.Model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private TrainerDAO trainerDAO;

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    public void createTrainer(Trainer trainer) {
        trainerDAO.createTrainer(trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        return  trainerDAO.updateTrainer(trainer);
    }

    public boolean deleteTrainer(Long id) {
        return trainerDAO.deleteTrainer(id);
    }

    public Trainer getTrainerById(Long id) {
        return trainerDAO.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAllTrainers();
    }
}
