package com.ai.skysmart.service;

import com.ai.skysmart.dto.CreateUserRequest;
import com.ai.skysmart.dto.UserDto;
import com.ai.skysmart.entity.User;

import java.util.List;

public interface UserService {
    UserDto createProfile(CreateUserRequest request);

    UserDto getProfile(Long userId);

    List<UserDto> getAllUsers();
    UserDto updateUser(Long userId, UserDto dto);
    void deleteUser(Long userId);

}
