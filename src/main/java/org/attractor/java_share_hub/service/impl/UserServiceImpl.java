package org.attractor.java_share_hub.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.java_share_hub.dto.UserDto;
import org.attractor.java_share_hub.exception.DatabaseOperationException;
import org.attractor.java_share_hub.exception.RecordAlreadyExistsException;
import org.attractor.java_share_hub.exception.UserNotFoundException;
import org.attractor.java_share_hub.model.Role;
import org.attractor.java_share_hub.repository.UserRepository;
import org.attractor.java_share_hub.service.RoleService;
import org.attractor.java_share_hub.service.UserService;
import org.attractor.java_share_hub.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(UserDto userDto) {
        log.info("Registering user: {}", userDto.getEmail());

        if (userRepository.existsByEmail(userDto.getEmail().strip())) {
            throw new RecordAlreadyExistsException("User with this email already exists");
        }

        Role userRole = roleService.getDefaultUserRole();


        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role("USER")
                .roles(List.of(userRole))
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user", e);
            throw new DatabaseOperationException("Error creating user");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with this email does not exist"));
    }


}
