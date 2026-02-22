package com.example.sismportal.service.impl;

import com.example.sismportal.dto.AuthResponse;
import com.example.sismportal.dto.LoginRequest;
import com.example.sismportal.dto.StudentRegistrationRequest;
import com.example.sismportal.dto.StudentResponseDto;
import com.example.sismportal.exception.BadRequestException;
import com.example.sismportal.exception.UnauthorizedException;
import com.example.sismportal.model.Admin;
import com.example.sismportal.model.Role;
import com.example.sismportal.model.Student;
import com.example.sismportal.repository.AdminRepository;
import com.example.sismportal.repository.StudentRepository;
import com.example.sismportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentResponseDto registerStudent(StudentRegistrationRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return DtoMapper.toStudentResponse(studentRepository.save(student));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return studentRepository.findByEmail(request.getUsername())
                .map(student -> buildStudentLoginResponse(student, request.getPassword()))
                .orElseGet(() -> adminRepository.findByUsername(request.getUsername())
                        .map(admin -> buildAdminLoginResponse(admin, request.getPassword()))
                        .orElseThrow(() -> new UnauthorizedException("Invalid credentials")));
    }

    private AuthResponse buildStudentLoginResponse(Student student, String password) {
        if (!passwordEncoder.matches(password, student.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new AuthResponse("Login successful", student.getEmail(), Role.ROLE_STUDENT.name());
    }

    private AuthResponse buildAdminLoginResponse(Admin admin, String password) {
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new AuthResponse("Login successful", admin.getUsername(), admin.getRole().name());
    }
}
