package com.example.sismportal.controller;

import com.example.sismportal.dto.AuthResponse;
import com.example.sismportal.dto.LoginRequest;
import com.example.sismportal.dto.StudentRegistrationRequest;
import com.example.sismportal.dto.StudentResponseDto;
import com.example.sismportal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<StudentResponseDto> register(@Valid @RequestBody StudentRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerStudent(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
