package com.example.gym.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeFormatter {

  public static LocalDate getCorrectDateFormat(String rawDate){
    LocalDate date = null;
    while (date == null) {
      try {
        date = LocalDate.parse(rawDate);
      } catch (DateTimeParseException e) {
        log.info("Invalid date format. Please try again.");
        return date;
      }
    }
    return date;
  }
}
