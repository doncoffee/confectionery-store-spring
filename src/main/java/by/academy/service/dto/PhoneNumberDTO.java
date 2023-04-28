package by.academy.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Setter
@Builder
public class PhoneNumberDTO {
    Long id;

    @NotBlank(message = "Number must not be blank")
    @Pattern(regexp = "^\\+\\d{1,3}\\s\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message = "Invalid phone number")
    String number;
}
