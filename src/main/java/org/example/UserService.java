package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String create(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        log.info("Testing existence of {}", userFromDb);
        if (userFromDb != null) {
            log.info("User already exists");
            //model.put("message", "User exists!");
            return "registration";
        }
        log.info("User doesn't exist");
        if (Objects.equals(user.getUsername(), "admin"))
            user.setRoles(Collections.singleton(Role.ADMIN));
        else
            user.setRoles(Collections.singleton(Role.USER));
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

}
