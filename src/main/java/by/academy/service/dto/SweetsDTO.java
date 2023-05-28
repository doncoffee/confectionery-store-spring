package by.academy.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

import static by.academy.util.Constants.*;

@Value
@Setter
@Builder
public class SweetsDTO {
    Long id;

    @DecimalMin(value = DECIMAL_MIN, message = PRICE_MUST_BE_GREATER_THAN_0)
    @NotNull(message = PRICE_MUST_NOT_BE_BLANK)
    Double price;

    @NotBlank(message = TYPE_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 100, message = TYPE_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    String type;

    @DecimalMin(value = DECIMAL_MIN, message = WEIGHT_MUST_BE_GREATER_THAN_0)
    @NotNull(message = WEIGHT_MUST_NOT_BE_BLANK)
    Double weight;

    Long brandId;

    String brandName;

    @NotBlank(message = COMPOSITION_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 255, message = COMPOSITION_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    String composition;

    Long storeId;

    String storeName;

    Long supplierId;

    String supplierName;
}
