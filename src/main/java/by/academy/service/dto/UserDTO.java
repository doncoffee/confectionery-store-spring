package by.academy.service.dto;

import by.academy.entity.Role;
import by.academy.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    Long id;
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 64, message = "Type size must be between {min} and {max}")
    @UniqueUsername
    String username;
    @NotBlank
    String rawPassword;
    Role role;
}
