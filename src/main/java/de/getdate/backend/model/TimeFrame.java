package de.getdate.backend.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeFrame {
  private LocalDateTime start;

  private LocalDateTime end;

  public static TimeFrame of(LocalDateTime start, LocalDateTime end) {
    return new TimeFrame(start, end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    TimeFrame other = (TimeFrame) obj;
    return Objects.equals(start, other.start) && Objects.equals(end, other.end);
  }



}
