package org.example.dto;

import org.example.enums.RoleEnum;
import org.example.enums.UserStatusEnum;

public record UserResponseDTO(Long id,
                              RoleEnum role,
                              String login,
                              String name,
                              UserStatusEnum userStatus) {
}
