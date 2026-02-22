package com.example.sismportal.service.impl;

import com.example.sismportal.dto.InternshipResponseDto;
import com.example.sismportal.dto.SkillDto;
import com.example.sismportal.dto.StudentResponseDto;
import com.example.sismportal.model.Internship;
import com.example.sismportal.model.Skill;
import com.example.sismportal.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

final class DtoMapper {

    private DtoMapper() {}

    static SkillDto toSkillDto(Skill skill) {
        return SkillDto.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .build();
    }

    static StudentResponseDto toStudentResponse(Student student) {
        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .skills(student.getSkills().stream().map(DtoMapper::toSkillDto).collect(Collectors.toSet()))
                .build();
    }

    static InternshipResponseDto toInternshipResponse(Internship internship) {
        Set<SkillDto> skills = internship.getRequiredSkills().stream()
                .map(DtoMapper::toSkillDto)
                .collect(Collectors.toSet());

        return InternshipResponseDto.builder()
                .id(internship.getId())
                .title(internship.getTitle())
                .organization(internship.getOrganization())
                .requiredSkills(skills)
                .source(internship.getSource())
                .externalLink(internship.getExternalLink())
                .createdAt(internship.getCreatedAt())
                .postedBy(internship.getCreatedBy().getUsername())
                .build();
    }
}
