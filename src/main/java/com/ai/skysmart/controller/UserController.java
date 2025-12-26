package com.ai.skysmart.controller;

import com.ai.skysmart.config.CusstomUser;
import com.ai.skysmart.dto.CreateUserRequest;
import com.ai.skysmart.dto.LoginRequest;
import com.ai.skysmart.dto.UserDto;
import com.ai.skysmart.entity.User;
import com.ai.skysmart.service.JwtService;
import com.ai.skysmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/profile")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public  ResponseEntity<UserDto> getUserProfileById(@PathVariable("id") Long id){
        return  new ResponseEntity<>(userService.getProfile(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public  ResponseEntity<List<UserDto>> getAllUserProfile(){
        return  new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long id, @RequestBody UserDto dto)
    {
        return  new ResponseEntity<>(userService.updateUser(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> DeleteUserProfile(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return  new ResponseEntity<>("Profile deleted Successfully", HttpStatus.OK);
    }
}
