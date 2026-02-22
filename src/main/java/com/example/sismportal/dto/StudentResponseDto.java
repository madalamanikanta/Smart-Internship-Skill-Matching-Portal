package com.example.sismportal.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private Set<SkillDto> skills;
}
