package com.example.sismportal.service;

import com.example.sismportal.dto.InternshipRequestDto;
import com.example.sismportal.dto.InternshipResponseDto;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface InternshipService {
    InternshipResponseDto createInternship(InternshipRequestDto requestDto);
    InternshipResponseDto updateInternship(Long internshipId, InternshipRequestDto requestDto);
    void deleteInternship(Long internshipId);
    InternshipResponseDto getInternshipById(Long internshipId);
    Page<InternshipResponseDto> getAllInternships(int page, int size, String sortBy, String direction);
    Page<InternshipResponseDto> filterBySkills(Set<String> skills, int page, int size, String sortBy, String direction);
}
