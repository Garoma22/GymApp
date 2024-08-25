package com.example.gymApp.Service;

import com.example.gymApp.Dao.TrainingDAO;
import com.example.gymApp.Model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    private  TrainingDAO trainingDAO;

//    @Autowired
//    public TrainingService(TrainingDAO trainingDAO) {
//        this.trainingDAO = trainingDAO;
//    }
    @Autowired
    public void setTrainingDAO(TrainingDAO trainingDAO){
        this.trainingDAO = trainingDAO;
    }


    public void createTraining(Training training) {
        // Логика для создания тренировки
        trainingDAO.createTraining(training);
    }

    public void updateTraining(Training training) {
        // Логика для обновления тренировки
        trainingDAO.updateTraining(training);
    }

    public void deleteTraining(Long id) {
        // Логика для удаления тренировки
        trainingDAO.deleteTraining(id);
    }

    public Training getTrainingById(Long id) {
        // Логика для получения тренировки по ID
        return trainingDAO.getTrainingById(id);
    }

    public List<Training> getAllTrainings() {
        // Логика для получения всех тренировок
        return trainingDAO.getAllTrainings();
    }
}
