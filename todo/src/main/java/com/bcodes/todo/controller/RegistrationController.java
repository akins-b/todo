package com.bcodes.todo.controller;

import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.repository.MyUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RegistrationController {

    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder) {
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    public MyUser registerUser(@RequestBody MyUser user) {
        user = new MyUser(null, user.username(), passwordEncoder.encode(user.password()));
        return myUserRepository.save(user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<MyUser> findUserByUsername(@PathVariable String username) {
        Optional<MyUser> user = myUserRepository.findUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
