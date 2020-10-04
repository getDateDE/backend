package de.getdate.backend.data;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity<ID> {
  @EmbeddedId
  protected ID id;

  @Column(name = "creation_date")
  protected LocalDateTime creationDate;

  @Column(name = "modified_data")
  protected LocalDateTime modifiedDate;

  @PrePersist
  public void onPrePersist() {
    creationDate = LocalDateTime.now();
  }

  @PreUpdate
  public void onPreUpdate() {
    modifiedDate = LocalDateTime.now();
  }

}
