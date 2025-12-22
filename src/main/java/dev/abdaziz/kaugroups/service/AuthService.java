package dev.abdaziz.kaugroups.service;

import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.User;
import dev.abdaziz.kaugroups.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User register(String name, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .gender(Gender.UNKNOWN)
                .build();
        return userRepository.save(user);
    }
    
}

