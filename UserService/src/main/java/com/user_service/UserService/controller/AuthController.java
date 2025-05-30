package com.user_service.UserService.controller;

import com.user_service.UserService.config.JwtUtil;
import com.user_service.UserService.dto.JwtResponseDTO;
import com.user_service.UserService.dto.LoginRequest;
import com.user_service.UserService.dto.RegisterRequest;
import com.user_service.UserService.dto.UserResponse;
import com.user_service.UserService.model.RefreshToken;
import com.user_service.UserService.service.AuthService;
import com.user_service.UserService.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request, "user"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/getuser")
    public ResponseEntity<UserResponse> getUserId(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getUser(userDetails.getUsername()));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody String token) {

        RefreshToken refreshed = refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Refresh Token is not in DB or expired"));

        String newAccessToken = jwtUtil.generateAccessToken(refreshed.getUser());

        JwtResponseDTO response = JwtResponseDTO.builder()
                .accessToken(newAccessToken)
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/auth/getuser/{id}")
    ResponseEntity<UserResponse> getUserById(@RequestParam UUID id){
        return ResponseEntity.ok(authService.getUserById(id));
    }
}
