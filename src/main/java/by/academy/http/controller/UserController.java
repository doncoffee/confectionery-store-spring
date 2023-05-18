package by.academy.http.controller;

import by.academy.entity.Role;
import by.academy.service.UserService;
import by.academy.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static by.academy.util.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(USER_PATH)
public class UserController {
    private final UserService userService;

    @GetMapping(REGISTRATION)
    public String registration(Model model) {
        model.addAttribute(ROLES, Role.values());
        return REGISTRATION1;
    }

    @PostMapping(CREATE_USER)
    public String createUser(@ModelAttribute @Validated UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            return REGISTRATION1;
        } else {
            userService.createUser(userDTO);
            return LOGIN;
        }
    }

    @GetMapping
    public String findCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserDTO> user = userService.findUserByUsername(username);

        if (user.isEmpty()) {
            String oauth2Email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute(EMAIL);
            user = userService.findUserByUsername(oauth2Email);
        }

        model.addAttribute(USER, user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return USER;
    }
}
