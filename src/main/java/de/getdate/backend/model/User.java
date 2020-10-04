package de.getdate.backend.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import de.getdate.backend.data.BaseEntity;
import de.getdate.backend.data.StringId;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@UserDefinition
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class User extends BaseEntity<StringId> {
  @Username
  private String email;

  @Password
  private String password;

  private String firstname;
  private String lastname;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
  private List<Slot> slots;

  @Roles
  private String roles = "user";

  @OneToOne(cascade = CascadeType.ALL)
  private Institution institution;

  {
    id = StringId.randomNumeric();
  }

}
