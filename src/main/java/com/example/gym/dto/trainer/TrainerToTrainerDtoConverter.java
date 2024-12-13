package com.example.gym.dto.trainer;

import com.example.gym.model.Trainer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainerToTrainerDtoConverter implements Converter<Trainer, TrainerDto> {

  @Override
  public TrainerDto convert(Trainer trainer) {
    return new TrainerDto(
        trainer.getUser().getUsername(),
        trainer.getUser().getFirstName(),
        trainer.getUser().getLastName(),
        trainer.getSpecialization().getName()
    );
  }
}
