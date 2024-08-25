package com.example.gymApp.Service;

import com.example.gymApp.Dao.TraineeDAO;
import com.example.gymApp.Model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {



//    private final TraineeDAO traineeDAO;

//    @Autowired
//    public TraineeService(TraineeDAO traineeDAO) {
//        this.traineeDAO = traineeDAO;
//    }



private  TraineeDAO traineeDAO;

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO){
        this.traineeDAO = traineeDAO;
    }




    public void createTrainee(Trainee trainee) {
        // Дополнительная логика, если требуется
        traineeDAO.createTrainee(trainee);
    }



    public boolean updateTrainee(Trainee trainee) {
        return traineeDAO.updateTrainee(trainee);
    }

    public boolean deleteTrainee(Long id) {
        // Логика удаления, если нужно
        return traineeDAO.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id) {
        // Можно добавить проверку на null или существование
        return traineeDAO.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        // Дополнительная логика обработки списка, если нужно
        return traineeDAO.getAllTrainees();
    }


}