package com.ai.skysmart.config;

import com.ai.skysmart.dto.CreateUserRequest;
import com.ai.skysmart.dto.LoginRequest;
import com.ai.skysmart.dto.OtpLoginRequest;
import com.ai.skysmart.dto.UserDto;
import com.ai.skysmart.entity.User;
import com.ai.skysmart.repository.UserRepository;
import com.ai.skysmart.service.JwtService;
import com.ai.skysmart.service.OtpService;
import com.ai.skysmart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpService otpService;


    //Register
    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  new ResponseEntity<>(userService.createProfile(user), HttpStatus.CREATED);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CusstomUser principal = (CusstomUser) auth.getPrincipal();

        String role = "ROLE_" + principal.getUser().getRole().name();

        String token = jwtService.generateToken(principal.getUsername(), role);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    //Login Using Otp
    @PostMapping("/auth/login/otp")
    public ResponseEntity<String> loginUsingOtp(@Valid @RequestBody OtpLoginRequest request) {
       Boolean valid= otpService.verifyOtp(request.getPhone(), request.getOtp());

        if(!valid){
            return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
        }
        User user= userRepository.findByPhone(request.getPhone())
                    .orElseThrow(()->new RuntimeException("User Not Found"));


        String role = "ROLE_" + user.getRole().name();

        String token = jwtService.generateToken(user.getEmail(), role);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }



}
