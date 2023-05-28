package by.academy.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static by.academy.util.Constants.DEFAULT_SUCCESS_URL;
import static by.academy.util.Constants.HOME;

@Controller
public class HomeController {

    @GetMapping(DEFAULT_SUCCESS_URL)
    public String getHomePage() {
        return HOME;
    }
}
