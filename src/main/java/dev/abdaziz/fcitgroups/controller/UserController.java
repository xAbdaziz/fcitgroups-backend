package dev.abdaziz.fcitgroups.controller;

import dev.abdaziz.fcitgroups.model.Gender;
import dev.abdaziz.fcitgroups.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/gender")
    public ResponseEntity<Void> updateGender(@PathVariable UUID id, @RequestBody GenderUpdateRequest request) {
        userService.updateGender(id, request.gender());
        return ResponseEntity.ok().build();
    }

    public record GenderUpdateRequest(Gender gender) {}
}
