package by.academy.service.dto;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class ChocolateDTO {
    Long id;
    Double price;
    String type;
    Double weight;
    Long brandId;
    String brandName;
    String composition;
    Long storeId;
    String storeName;
    Long supplierId;
    String supplierName;
}
