package de.getdate.backend.dto;

import java.util.List;
import java.util.stream.Collectors;
import de.getdate.backend.model.Institution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionDto {
  private String id;
  private String phone;
  private String name;
  private AddressDto address;
  private String description;
  private List<String> employees;

  public static InstitutionDto fromSingle(Institution institution) {
    return builder().id(institution.getId().getValue()).name(institution.getName())
        .phone(institution.getPhone()).address(AddressDto.from(institution.getAddress()))
        .description(institution.getDescription()).employees(institution.getEmployeeNames()).build();
  }

  public static List<InstitutionDto> fromList(List<Institution> institution) {
    return institution.stream().map(InstitutionDto::fromSingle).collect(Collectors.toList());
  }

}
