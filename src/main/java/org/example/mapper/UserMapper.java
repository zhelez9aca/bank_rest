package org.example.mapper;

import org.example.dto.UserResponseDTO;
import org.example.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toResponse(Users user){
        return new UserResponseDTO(
                user.getId(),
                user.getRole(),
                user.getLogin(),
                user.getName(),
                user.getUserStatus()
        );
    }
}
