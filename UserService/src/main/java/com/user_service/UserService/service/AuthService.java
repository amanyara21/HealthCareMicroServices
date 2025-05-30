package com.user_service.UserService.service;

import com.user_service.UserService.config.JwtUtil;
import com.user_service.UserService.dto.JwtResponseDTO;
import com.user_service.UserService.dto.LoginRequest;
import com.user_service.UserService.dto.RegisterRequest;
import com.user_service.UserService.dto.UserResponse;
import com.user_service.UserService.model.RefreshToken;
import com.user_service.UserService.model.Role;
import com.user_service.UserService.model.User;
import com.user_service.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
     RefreshTokenService refreshTokenService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    public JwtResponseDTO register(RegisterRequest request, String role) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshToken.getToken()).build();
    }

    public JwtResponseDTO authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshToken.getToken()).build();
    }

    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return new UserResponse(user);
    }

    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserResponse(user);
    }
}
