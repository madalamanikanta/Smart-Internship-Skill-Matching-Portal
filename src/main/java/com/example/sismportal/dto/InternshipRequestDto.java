package com.example.sismportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class InternshipRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String organization;

    @NotEmpty
    private Set<Long> requiredSkillIds;

    @NotBlank
    private String source;

    @NotBlank
    private String externalLink;

    @NotNull
    private Long adminId;
}
