package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.services.UserService;
import org.example.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @GetMapping("/registration")
    public String registration() {
        log.info("Registration page visited");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, /*Map<String, Object>*/ Model model) {
        log.info("Starting registration");
        return userService.create(user, model);
    }

    @GetMapping("/login")
    public String login() {
        log.info("Login page visited");
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout page visited");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/logout-success";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        log.info("Logout-ed");
        return "logout";
    }
}