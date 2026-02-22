package com.example.sismportal.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class InternshipResponseDto {
    private Long id;
    private String title;
    private String organization;
    private Set<SkillDto> requiredSkills;
    private String source;
    private String externalLink;
    private LocalDateTime createdAt;
    private String postedBy;
}
