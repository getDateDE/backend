package de.getdate.backend.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.StringId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Address extends BaseEntity<StringId> {
  private String country;
  private String city;
  private String zipCode;
  private String street;
  private String houseNumber;
  private String latitude;
  private String longtitude;

  @OneToOne(mappedBy = "address")
  private Institution institution;

  {
    id = StringId.randomNumeric();
  }

}
