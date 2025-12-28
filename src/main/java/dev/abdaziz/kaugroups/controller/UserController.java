package dev.abdaziz.kaugroups.controller;

import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.User;
import dev.abdaziz.kaugroups.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/me/gender")
    public ResponseEntity<Void> updateGender(
            @AuthenticationPrincipal User user,
            @RequestBody GenderUpdateRequest request
    ) {
        userService.updateGender(user.getId(), request.gender());
        return ResponseEntity.ok().build();
    }

    public record GenderUpdateRequest(Gender gender) {}
}

