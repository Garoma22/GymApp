package com.example.gymApp.Service;

import com.example.gymApp.Dao.TrainerDAO;
import com.example.gymApp.Model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private TrainerDAO trainerDAO;

//    @Autowired
//    public TrainerService(TrainerDAO trainerDAO) {
//        this.trainerDAO = trainerDAO;
//    }

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    public void createTrainer(Trainer trainer) {
        // Дополнительная логика, если требуется
        trainerDAO.createTrainer(trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        // Логика обновления данных тренера
        return  trainerDAO.updateTrainer(trainer);
    }

    public boolean deleteTrainer(Long id) {
        // Логика удаления тренера по ID
        return trainerDAO.deleteTrainer(id);
    }

    public Trainer getTrainerById(Long id) {
        // Можно добавить проверку на null или существование тренера
        return trainerDAO.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers() {
        // Логика получения списка всех тренеров
        return trainerDAO.getAllTrainers();
    }
}
