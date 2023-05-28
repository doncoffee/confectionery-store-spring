package by.academy.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

import static by.academy.util.Constants.INVALID_PHONE_NUMBER;
import static by.academy.util.Constants.NUMBER_MUST_NOT_BE_BLANK;

@Value
@Setter
@Builder
public class PhoneNumberDTO {
    Long id;

    @NotBlank(message = NUMBER_MUST_NOT_BE_BLANK)
    @Pattern(regexp = "^\\+\\d{1,3}\\s\\(\\d{3}\\)\\s\\d{3}-\\d{4}$", message = INVALID_PHONE_NUMBER)
    String number;
}
