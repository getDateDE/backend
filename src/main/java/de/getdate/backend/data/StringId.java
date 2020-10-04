package de.getdate.backend.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.RandomStringUtils;
import de.getdate.backend.data.jpa.SimpleValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@SimpleValueObject
@Embeddable
@Getter @EqualsAndHashCode
public class StringId implements Serializable {
  private static final int DEFAULT_LENGTH = 8;

  @Column(name = "id", columnDefinition = "varchar(128)")
  private String value;

  private StringId() {
  }

  private StringId(String value) {
    this.value = value;
  }

  public static StringId randomNumeric() {
    return valueOf(RandomStringUtils.randomNumeric(DEFAULT_LENGTH));
  }

  public static StringId valueOf(String value) {
    return new StringId(value);
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || !(object instanceof StringId)) {
      return false;
    }

    StringId other = (StringId) object;
    return value.equals(other.value);
  }

}
