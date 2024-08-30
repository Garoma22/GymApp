//package com.example.gymApp.Controller;
//
//import com.example.gymApp.Model.Trainee;
//import com.example.gymApp.Service.Facade;
//import com.example.gymApp.Service.TraineeService;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//
//@Slf4j
//@RestController
//@RequestMapping("/trainees")
//public class TraineeController {
//
////    private final TraineeService traineeService;
//
//    private   Facade facade;
//
////    public TraineeController(Facade facade) {
////        this.facade = facade;
////    }
////
//
//    @Autowired
//    public void setFacade(Facade facade){
//        this.facade = facade;
//    }
//
////    @Autowired
////    public TraineeController(TraineeService traineeService, Facade facade) {
////
////        this.facade = facade;
////    }
//
//
//
//    @PostMapping("/create")
//    public void createTrainee(@RequestBody Trainee trainee) {
//        facade.createTrainee(trainee);
//    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateTrainee(@PathVariable("id") Long id, @RequestBody Trainee trainee) {
//        trainee.setId(id);
//        boolean updated = facade.updateTrainee(trainee);
//
//        if (updated) {
//            return ResponseEntity.ok("Trainee updated successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee with id " + id + " not found");
//        }
//    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteTrainee(@PathVariable ("id") Long id) {
//
//        boolean updated = facade.deleteTrainee(id);
//
//        if (updated){
//            return ResponseEntity.ok ("Trainee deleted successfully");
//        }
//        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee with id " + id + " not found");
//    }
//
//    @GetMapping("/select/{id}")
//    public ResponseEntity<Trainee> getTrainee(@PathVariable("id") Long id) {
//        Trainee trainee = facade.getTraineeById(id);
//        if (trainee != null) {
//           log.info("Trainee found: {}", trainee);
//            return new ResponseEntity<>(trainee, HttpStatus.OK);
//        } else {
//            log.info("Trainee NOT found");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/all")
//    public List<Trainee> getAllTrainees() {
//        return facade.getAllTrainees();
//    }
//}