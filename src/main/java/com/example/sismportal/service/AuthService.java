package com.example.sismportal.service;

import com.example.sismportal.dto.AuthResponse;
import com.example.sismportal.dto.LoginRequest;
import com.example.sismportal.dto.StudentRegistrationRequest;
import com.example.sismportal.dto.StudentResponseDto;

public interface AuthService {
    StudentResponseDto registerStudent(StudentRegistrationRequest request);
    AuthResponse login(LoginRequest request);
}
