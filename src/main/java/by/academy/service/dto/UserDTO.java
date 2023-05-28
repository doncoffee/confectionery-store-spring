package by.academy.service.dto;

import by.academy.entity.Role;
import by.academy.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static by.academy.util.Constants.*;
import static by.academy.util.Constants.PASSWORD_MUST_NOT_BE_BLANK;

@Value
@Builder
public class UserDTO {
    Long id;
    @NotBlank(message = USERNAME_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 64, message = USERNAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    @UniqueUsername
    String username;
    @NotBlank(message = PASSWORD_MUST_NOT_BE_BLANK)
    String rawPassword;
    @DateTimeFormat(pattern = YYYY_MM_DD)
    LocalDate birthDate;
    @NotBlank(message = FIRST_NAME_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 64, message = FIRST_NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    String firstname;
    @NotBlank(message = LAST_NAME_MUST_NOT_BE_BLANK)
    @Size(min = 3, max = 64, message = LAST_NAME_SIZE_MUST_BE_BETWEEN_MIN_AND_MAX)
    String lastname;
    Role role;
}
