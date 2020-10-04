package de.getdate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotCreationRequest {
  private int dateTimestamp;
  private int durationInMinutes;
  private String employee;

}
