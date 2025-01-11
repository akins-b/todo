package com.bcodes.todo.controller;

import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.repository.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder){
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; // Refers to register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        var user = new MyUser(null, username, passwordEncoder.encode(password));
        myUserRepository.save(user);
        return "redirect:/login";
    }
}
