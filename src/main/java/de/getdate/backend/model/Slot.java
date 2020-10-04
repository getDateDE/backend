package de.getdate.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.StringId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "slots")
@Getter @Setter
public class Slot extends BaseEntity<StringId> {
  @ManyToOne(cascade = CascadeType.MERGE)
  private Institution institution;

  private long dateTimestamp;

  private int durationInMinutes;

  @ManyToOne(cascade = CascadeType.MERGE)
  private User user;

  private String employee;

  {
    id = StringId.randomNumeric();
  }

  public boolean isOccupied() {
    return user != null;
  }

  public boolean isFree() {
    return !isOccupied();
  }

}
