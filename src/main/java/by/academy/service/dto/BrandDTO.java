package by.academy.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class BrandDTO {
    Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, max = 100, message = "Name size must be between {min} and {max}")
    String name;

}
