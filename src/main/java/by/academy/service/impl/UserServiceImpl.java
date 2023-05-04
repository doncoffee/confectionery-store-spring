package by.academy.service.impl;

import by.academy.mapper.impl.UserMapper;
import by.academy.repository.UserRepository;
import by.academy.service.UserService;
import by.academy.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return Optional.of(userDTO)
                .map(userMapper::mapToEntity)
                .map(userRepository::save)
                .map(userMapper::mapToDTO)
                .orElseThrow();
    }
}
