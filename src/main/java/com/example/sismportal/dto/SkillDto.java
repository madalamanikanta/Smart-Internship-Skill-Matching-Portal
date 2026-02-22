package com.example.sismportal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillDto {
    private Long id;
    private String skillName;
}
