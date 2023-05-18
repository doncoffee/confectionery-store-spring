package by.academy.handlers;

import by.academy.entity.Role;
import by.academy.service.UserService;
import by.academy.service.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static by.academy.util.Constants.*;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute(EMAIL);
        String firstName = oauthUser.getAttribute(GIVEN_NAME);
        String lastName = oauthUser.getAttribute(FAMILY_NAME);

        if (!userService.existsByUsername(email)) {
            UserDTO newUser = UserDTO.builder()
                    .username(email)
                    .firstname(firstName)
                    .lastname(lastName)
                    .role(Role.USER)
                    .build();
            userService.createUser(newUser);
        }

        response.sendRedirect(DEFAULT_SUCCESS_URL);
    }
}