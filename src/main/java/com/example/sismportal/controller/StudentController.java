package com.example.sismportal.controller;

import com.example.sismportal.dto.SkillDto;
import com.example.sismportal.dto.StudentResponseDto;
import com.example.sismportal.service.StudentService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<StudentResponseDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(studentService.getProfileByEmail(authentication.getName()));
    }

    @GetMapping("/skills")
    public ResponseEntity<Set<SkillDto>> getSkills(Authentication authentication) {
        return ResponseEntity.ok(studentService.getStudentSkills(authentication.getName()));
    }

    @PostMapping("/skills")
    public ResponseEntity<Set<SkillDto>> addSkills(Authentication authentication,
                                                   @RequestBody @NotEmpty Set<Long> skillIds) {
        return ResponseEntity.ok(studentService.addSkillsToStudent(authentication.getName(), skillIds));
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Set<SkillDto>> removeSkill(Authentication authentication,
                                                     @PathVariable Long skillId) {
        return ResponseEntity.ok(studentService.removeSkillFromStudent(authentication.getName(), skillId));
    }
}
