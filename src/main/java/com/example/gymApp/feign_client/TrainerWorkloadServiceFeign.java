package com.example.gymApp.feign_client;



import com.example.gymApp.dto.training.TrainerWorkloadServiceDto;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "trainer-workload-service", url = "${trainer-workload-service.url}")

@FeignClient(name = "trainer-workload-service", url = "http://localhost:8085")
public interface TrainerWorkloadServiceFeign {

  @PostMapping("/trainings")
  void handleTraining(@RequestBody TrainerWorkloadServiceDto request);

//  @PostMapping("/getAauth")
//  Stting getAuth("login", "passl"); // todo - need to create the class
}



