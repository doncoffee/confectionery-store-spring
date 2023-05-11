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

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute @Validated UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration";
        } else {
            userService.createUser(userDTO);
            return "login";
        }
    }

    @GetMapping
    public String findCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserDTO> user = userService.findUserByUsername(username);

        if (user.isEmpty()) {
            String oauth2Email = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
            user = userService.findUserByUsername(oauth2Email);
        }

        model.addAttribute("user", user.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "user";
    }
}
