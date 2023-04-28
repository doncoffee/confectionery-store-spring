package by.academy.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class SweetsDTO {
    Long id;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @NotNull(message = "Price must not be blank")
    Double price;

    @NotBlank(message = "Type must not be blank")
    @Size(min = 3, max = 100, message = "Type size must be between {min} and {max}")
    String type;

    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
    @NotNull(message = "Weight must not be blank")
    Double weight;

    Long brandId;

    String brandName;

    @NotBlank(message = "Composition must not be blank")
    @Size(min = 3, max = 255, message = "Composition size must be between {min} and {max}")
    String composition;

    Long storeId;

    String storeName;

    Long supplierId;

    String supplierName;
}
