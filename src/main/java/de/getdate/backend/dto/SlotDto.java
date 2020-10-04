package de.getdate.backend.dto;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import de.getdate.backend.model.Slot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotDto {
  private long dateTimestamp;
  private int durationInMinutes;
  private String employee;
  private UserDto slotOwner;

  public static SlotDto from(Slot slot) {
    SlotDtoBuilder builder = builder().dateTimestamp(slot.getDateTimestamp())
        .durationInMinutes(slot.getDurationInMinutes()).employee(slot.getEmployee());
    if (slot.getUser() != null) {
      builder.slotOwner(UserDto.from(slot.getUser()));
    }
    return builder.build();
  }

  public static List<SlotDto> from(List<Slot> slot) {
    return slot.stream().map(SlotDto::from).collect(Collectors.toList());
  }

}
