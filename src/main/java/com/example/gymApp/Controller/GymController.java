package com.example.gymApp.Controller;


import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import com.example.gymApp.Service.Facade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class GymController {


    @Autowired
    private final Facade facade;

    public GymController(Facade facade) {
        this.facade = facade;
    }

    @GetMapping(value = "/trainees", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Trainee>> getAllTrainees() {

        ArrayList<Trainee> trainees = (ArrayList<Trainee>) facade.getAllTrainees();
        return ResponseEntity.ok(trainees);

    }

    @GetMapping("/trainers")
    public List<Trainer> getAllTrainers() {
        return facade.getAllTrainers();
    }

    @GetMapping("/trainings")
    public List<Training> getAllTrainings() {
        return facade.getAllTrainings();
    }
}
