package by.academy.service.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class AddressDTO {
    Long id;
    String name;
}
