package com.example.sismportal.service.impl;

import com.example.sismportal.dto.SkillDto;
import com.example.sismportal.dto.StudentResponseDto;
import com.example.sismportal.exception.ResourceNotFoundException;
import com.example.sismportal.model.Skill;
import com.example.sismportal.model.Student;
import com.example.sismportal.repository.SkillRepository;
import com.example.sismportal.repository.StudentRepository;
import com.example.sismportal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getProfileByEmail(String email) {
        return DtoMapper.toStudentResponse(getStudentByEmail(email));
    }

    @Override
    @Transactional
    public Set<SkillDto> addSkillsToStudent(String email, Set<Long> skillIds) {
        Student student = getStudentByEmail(email);
        Set<Skill> skills = skillIds.stream()
                .map(this::findSkill)
                .collect(Collectors.toSet());

        student.getSkills().addAll(skills);
        Student updated = studentRepository.save(student);
        return updated.getSkills().stream().map(DtoMapper::toSkillDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<SkillDto> removeSkillFromStudent(String email, Long skillId) {
        Student student = getStudentByEmail(email);
        Skill skill = findSkill(skillId);

        student.getSkills().remove(skill);
        Student updated = studentRepository.save(student);
        return updated.getSkills().stream().map(DtoMapper::toSkillDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SkillDto> getStudentSkills(String email) {
        return getStudentByEmail(email).getSkills().stream().map(DtoMapper::toSkillDto).collect(Collectors.toSet());
    }

    private Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for email: " + email));
    }

    private Skill findSkill(Long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found for id: " + skillId));
    }
}
