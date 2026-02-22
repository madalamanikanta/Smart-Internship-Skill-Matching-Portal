package com.example.sismportal.service;

import com.example.sismportal.dto.SkillDto;
import com.example.sismportal.dto.StudentResponseDto;

import java.util.Set;

public interface StudentService {
    StudentResponseDto getProfileByEmail(String email);
    Set<SkillDto> addSkillsToStudent(String email, Set<Long> skillIds);
    Set<SkillDto> removeSkillFromStudent(String email, Long skillId);
    Set<SkillDto> getStudentSkills(String email);
}
