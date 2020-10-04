package de.getdate.backend.dto;

import de.getdate.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
  private String email;
  private String firstname;
  private String lastname;

  public static UserDto from(User user) {
    return builder().email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).build();
  }

}
