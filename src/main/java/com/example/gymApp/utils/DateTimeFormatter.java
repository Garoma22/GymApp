package com.example.gymApp.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class DateTimeFormatter {

  public static LocalDate getCorrectDateFormat(String rawDate){
    LocalDate date = null;
    while (date == null) {
      try {
        date = LocalDate.parse(rawDate);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
        return date;
      }
    }
    return date;
  }
}
