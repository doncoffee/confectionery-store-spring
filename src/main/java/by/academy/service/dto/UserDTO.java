package by.academy.service.dto;

import by.academy.entity.Role;
import by.academy.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@Builder
public class UserDTO {
    Long id;
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 64, message = "Username size must be between {min} and {max}")
    @UniqueUsername
    String username;
    @NotBlank
    String rawPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
    @NotBlank(message = "First name must not be blank")
    @Size(min = 3, max = 64, message = "First name size must be between {min} and {max}")
    String firstname;
    @NotBlank(message = "Last name must not be blank")
    @Size(min = 3, max = 64, message = "Last name size must be between {min} and {max}")
    String lastname;
    Role role;
}
