package org.attractor.java_share_hub.service;

import org.attractor.java_share_hub.dto.UserDto;

public interface UserService {
    boolean existsByEmail(String email);

    void registerUser(UserDto userDto);
}
