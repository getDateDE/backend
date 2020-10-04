package de.getdate.backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.StringId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "institutions")
@Getter @Setter
@NoArgsConstructor
public class Institution extends BaseEntity<StringId> {
  private String phone;
  private String name;
  private String description;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "institution", orphanRemoval = true)
  private List<Slot> slots;

  @ElementCollection
  @CollectionTable(name="employeeNames", joinColumns=@JoinColumn(name="id"))
  @Column(name="employeeName")
  private List<String> employeeNames;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "institution")
  private User user;

  {
    id = StringId.randomNumeric();
  }

  public StringId getId() {
    return id;
  }

  public List<Slot> getSlots() {
    return slots == null ? Collections.emptyList() : slots;
  }

  public void addEmployee(String name) {
    if (employeeNames == null) {
      employeeNames = new ArrayList<>();
    }

    employeeNames.add(name);
  }

}
