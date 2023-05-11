package by.academy.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static by.academy.util.Constants.LOGIN;
import static by.academy.util.Constants.LOGIN1;

@Controller
public class LoginController {

    @GetMapping(LOGIN)
    public String login() {
        return LOGIN1;
    }
}
