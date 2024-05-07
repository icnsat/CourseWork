package org.example.services;

import org.example.repositories.UserRepository;
import org.example.entities.Role;
import org.example.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String create(User user ,  /*Map<String, Object>*/ Model model) {
        User userFromDb = userRepository.findUserByUsername(user.getUsername());
        log.info("Testing existence of {}", userFromDb);
        if (userFromDb != null) {
            log.info("User already exists");
            model.addAttribute("userexists", "User exists!"); //put("message", "User exists!");
            return "registration";
        }
        log.info("User doesn't exist");
        if (Objects.equals(user.getUsername(), "admin"))
            user.setRoles(Collections.singleton(Role.ADMIN));
        else
            user.setRoles(Collections.singleton(Role.CLIENT));
        log.info("Role for new user applied");
        user.setActive(true);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("New user added");
        return "redirect:/login";
    }

    public List<User> readAll() {
        log.info("Read all user");
        return userRepository.findAll();
    }

    public User read(Long id) {
        log.info("Read user with id = {}", id);
        return userRepository.getReferenceById(id);
    }

    public boolean delete(Long id) {
        log.info("Delete user with id = {}", id);
        userRepository.deleteById(id);
        return true;
    }

    public User findByUsername(String username) {
        log.info("Read user with name = {}", username);
        return userRepository.findUserByUsername(username);
    }

    public User findUserById(Long id) {
        log.info("Read user with id = {}", id);
        return userRepository.findUserById(id);
    }
}
