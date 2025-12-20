package dev.abdaziz.fcitgroups.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import dev.abdaziz.fcitgroups.model.User;
import dev.abdaziz.fcitgroups.service.AuthService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user.getName(), user.getEmail()));
    }
    
}
