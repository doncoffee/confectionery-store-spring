package by.academy.service;

import by.academy.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    Optional<UserDTO> findUserByUsername(String username);
    UserDetails loadUserByUsername(String username);
    boolean existsByUsername(String username);
}
