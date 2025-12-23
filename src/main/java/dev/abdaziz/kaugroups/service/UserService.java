package dev.abdaziz.kaugroups.service;

import dev.abdaziz.kaugroups.exception.BusinessRuleViolationException;
import dev.abdaziz.kaugroups.exception.ResourceNotFoundException;
import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.User;
import dev.abdaziz.kaugroups.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void updateGender(UUID id, Gender gender) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (user.getGender() != Gender.UNKNOWN) {
            throw new BusinessRuleViolationException("User already has a gender assigned");
        }
        
        user.setGender(gender);
        userRepository.save(user);
    }
}

