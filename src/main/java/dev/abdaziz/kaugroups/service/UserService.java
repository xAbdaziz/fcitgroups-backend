package dev.abdaziz.kaugroups.service;

import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.User;
import dev.abdaziz.kaugroups.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void updateGender(UUID id, Gender gender) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.get().getGender() != Gender.UNKNOWN) {
            throw new IllegalArgumentException("User already has a gender");
        }
        user.get().setGender(gender);
        userRepository.save(user.get());
    }
}

