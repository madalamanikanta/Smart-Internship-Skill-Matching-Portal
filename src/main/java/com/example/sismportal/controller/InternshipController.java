package com.example.sismportal.controller;

import com.example.sismportal.dto.InternshipResponseDto;
import com.example.sismportal.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<Page<InternshipResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(internshipService.getAllInternships(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(internshipService.getInternshipById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<InternshipResponseDto>> filterBySkills(
            @RequestParam Set<String> skills,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(internshipService.filterBySkills(skills, page, size, sortBy, direction));
    }
}
