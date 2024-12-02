package com.example.gymApp.feign;


import com.example.gymApp.config.FeignClientConfig;
import com.example.gymApp.dto.training.TrainerWorkloadServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "trainer-workload-service", url = "${trainer-workload-service.url}")
//@FeignClient(name = "trainer-workload-service", url = "http://localhost:8085")


@FeignClient(name = "trainer-workload-service", configuration = FeignClientConfig.class)  //! Eureka does not need URLs
public interface TrainerWorkloadServiceFeign {

  @PostMapping("/trainers/workload")
  void handleTraining(@RequestBody TrainerWorkloadServiceDto request);

}



