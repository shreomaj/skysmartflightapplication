package com.ai.skysmart.service.impl;

import com.ai.skysmart.dto.CreateUserRequest;
import com.ai.skysmart.dto.UserDto;
import com.ai.skysmart.entity.User;
import com.ai.skysmart.repository.UserRepository;
import com.ai.skysmart.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createProfile(CreateUserRequest request) {
        User user=new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        User saved=userRepository.save(user);
        return modelMapper.map(saved, UserDto.class);
    }

    @Override
    public UserDto getProfile(Long userId) {
        User userById=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));

        return modelMapper.map(userById, UserDto.class);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(u-> modelMapper.map(u, UserDto.class)).toList();
    }

    @Override
    public UserDto updateUser(Long userId, UserDto dto) {
        User userById=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        userById.setPhone(dto.getPhone());
        userById.setName(dto.getName());
        User updated = userRepository.save(userById);
        return modelMapper.map(updated, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        userRepository.delete(user);


    }
}
