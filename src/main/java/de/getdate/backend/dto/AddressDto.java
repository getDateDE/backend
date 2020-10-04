package de.getdate.backend.dto;

import javax.json.bind.annotation.JsonbProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.getdate.backend.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
  private String latitude;
  private String longitude;
  private String country;
  private String city;

  @JsonbProperty("zip_code")
  private String zipCode;
  private String street;

  @JsonbProperty("house_number")
  private String houseNumber;

  public static AddressDto from(Address address) {
    return builder()
    .latitude(address.getLatitude())
    .longitude(address.getLongtitude())
    .country(address.getCountry())
    .city(address.getCity())
    .zipCode(address.getZipCode())
    .street(address.getStreet())
    .houseNumber(address.getHouseNumber()).build();
  }

}
