package by.academy.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

import static by.academy.util.Constants.NAME_MUST_NOT_BE_BLANK;
import static by.academy.util.Constants.NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX;

@Value
@Setter
@Builder
public class BrandDTO {
    Long id;

    @NotBlank(message = NAME_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 100, message = NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    String name;

}
