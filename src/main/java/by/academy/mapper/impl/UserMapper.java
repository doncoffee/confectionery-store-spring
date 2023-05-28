package by.academy.mapper.impl;

import by.academy.entity.User;
import by.academy.mapper.Mapper;
import by.academy.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDTO> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapToEntity(UserDTO object) {
        User user = User.builder()
                .id(object.getId())
                .username(object.getUsername())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .birthDate(object.getBirthDate())
                .role(object.getRole())
                .build();

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        return user;
    }

    @Override
    public UserDTO mapToDTO(User object) {
        return UserDTO.builder()
                .id(object.getId())
                .username(object.getUsername())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .birthDate(object.getBirthDate())
                .role(object.getRole())
                .build();
    }
}
