package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.UserResponseDTO;
import org.example.enums.UserStatusEnum;
import org.example.exception.BadAdminRequestException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponseDTO> getUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).map(userMapper::toResponse);
    }

    public void blockUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new BadAdminRequestException("User is not found"));
        if (user.getUserStatus() == UserStatusEnum.BLOCKED) {
            throw new BadAdminRequestException("User already blocked");
        }
        user.setUserStatus(UserStatusEnum.BLOCKED);
        userRepository.save(user);
    }

    public void activateUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new BadAdminRequestException("User is not found"));
        if (user.getUserStatus() == UserStatusEnum.ACTIVE) {
            throw new BadAdminRequestException("User already active");
        }
        user.setUserStatus(UserStatusEnum.ACTIVE);
        userRepository.save(user);
    }
}
