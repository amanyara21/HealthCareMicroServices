package com.user_service.UserService.controller;

import com.user_service.UserService.dto.JwtResponseDTO;
import com.user_service.UserService.dto.RegisterRequest;
import com.user_service.UserService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthService authService;

    @PostMapping("/create-doctor")
    public ResponseEntity<?> createDoctor(@RequestBody RegisterRequest request) {
        authService.register(request, "doctor");
        return ResponseEntity.ok("Doctor Created Successfully");
    }

    @PostMapping("/create-admin")
    public ResponseEntity<JwtResponseDTO> createAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request, "admin"));
    }
}

