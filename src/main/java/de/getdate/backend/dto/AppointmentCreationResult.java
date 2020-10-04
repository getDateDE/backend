package de.getdate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AppointmentCreationResult {
  public enum Type {
    SUCESSFULLY_CREATED, ALREADY_OCCUPIED, NOT_AVAILABLE;
  }

}
