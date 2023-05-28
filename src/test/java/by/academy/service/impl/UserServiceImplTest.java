package by.academy.service.impl;

import by.academy.entity.Role;
import by.academy.service.UserService;
import by.academy.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.util.MockUtil.createUserDTO;
import static by.academy.util.TestConstants.FAKE_USERNAME;
import static by.academy.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    private final UserService userService;

    @Autowired
    UserServiceImplTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void createUser() {
        UserDTO result = userService.createUser(createUserDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(USERNAME, result.getUsername());
    }

    @Test
    void findUserByUsername() {
        UserDTO userDTO = userService.createUser(createUserDTO());

        UserDTO foundUserDTO = userService.findUserByUsername(userDTO.getUsername())
                .orElseThrow();

        assertNotNull(foundUserDTO);
        assertEquals(USERNAME, foundUserDTO.getUsername());
        assertEquals(Role.USER, foundUserDTO.getRole());
    }

    @Test
    void existsByUsername() {
        UserDTO userDTO = userService.createUser(createUserDTO());

        boolean result1 = userService.existsByUsername(userDTO.getUsername());
        boolean result2 = userService.existsByUsername(FAKE_USERNAME);

        assertTrue(result1);
        assertFalse(result2);
    }
}