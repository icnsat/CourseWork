package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;


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
        log.info("Registration cite visited");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user/*, Map<String, Object> model*/) {
        log.info("Starting registration");
        return userService.create(user);
    }

    @GetMapping("/login")
    public String login() {
        log.info("Login cite visited");
        return "login";
    }

   /* @PostMapping("/login")
    public String testLogin(User user, Map<String, Object> model) {
        logger.info("Post login-");
        User userFromDb = userRepo.findAllByUsername(user.getUsername());
        logger.info("existence testing{}", userFromDb);
        if (userFromDb != null) {
            logger.info("exists");
            model.put("message", "User exists!");
            return "redirect:/home";
        }
        logger.info("doesn't exist!");

        return "redirect:/login";
    }*/

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}