package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.UserDto;
import org.attractor.java_share_hub.model.User;

public interface UserService {
    boolean existsByEmail(String email);

    void registerUser(UserDto userDto);

    User findUserByEmail(String email);

    UserDto getUserProfile(Long userId);

    UserDto getUserProfileAuth(org.springframework.security.core.userdetails.User principal);

}
