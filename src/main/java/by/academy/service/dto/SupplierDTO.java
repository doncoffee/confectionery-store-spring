package by.academy.service.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class SupplierDTO {
    Long id;
    String name;
    String contactPerson;
    Long addressId;
    String addressName;
    Long phoneNumberId;
    String phoneNumberNumber;
}
