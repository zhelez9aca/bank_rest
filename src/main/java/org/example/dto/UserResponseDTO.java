package org.example.dto;

import org.example.enums.Role;
import org.example.enums.UserStatus;

public record UserResponse(Long userId,
                           Role role,
                           String login,
                           String name,
                           UserStatus status
) {
}
