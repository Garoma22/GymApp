//package com.example.gymApp.Controller;
//
//import com.example.gymApp.Model.Trainer;
//import com.example.gymApp.Service.Facade;
//import com.example.gymApp.Service.TrainerService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/trainer")
//public class TrainerController {
//
////    private final TrainerService trainerService;
////    @Autowired
////    public TrainerController(TrainerService trainerService) {
////        this.trainerService = trainerService;
////    }
//
//
//
//
//    private Facade facade;
//
////    public TrainerController(Facade facade) {
////        this.facade = facade;
////    }
//
//@Autowired
//    public void setFacade(Facade facade){
//        this.facade = facade;
//    }
//
//    @GetMapping("/select/{id}")
//    public ResponseEntity<Trainer> getTrainerById(@PathVariable("id") Long id) {
//        Trainer trainer = facade.getTrainerById(id);
//        if (trainer != null) {
//
//            return new ResponseEntity<>(trainer, HttpStatus.OK);
//        } else {
//            log.info("There in no such trainer!");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<Trainer>> getAllTrainers() {
//        List<Trainer> trainers = facade.getAllTrainers();
//        if (!trainers.isEmpty()) {
//            return new ResponseEntity<>(trainers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
//
//    @PostMapping("/create")
//    public void createTrainer(@RequestBody Trainer trainer) {
//        facade.createTrainer(trainer);
//    }
//
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateTrainer(@PathVariable("id") Long id, @RequestBody Trainer trainer) {
//        trainer.setId(id);
//        boolean updated = facade.updateTrainer(trainer);
//
//        if (updated) {
//            return ResponseEntity.ok("Trainee updated successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee with id " + id + " not found");
//        }
//    }
//
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteTrainer(@PathVariable("id") Long id){
//        boolean updated = facade.deleteTrainer(id);
//
//        if(updated){
//            return ResponseEntity.ok("Trainer is successfully deleted");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer with id " + id + " not found");
//    }
//}
//
//
//
//
