package com.example.sismportal.controller;

import com.example.sismportal.dto.InternshipRequestDto;
import com.example.sismportal.dto.InternshipResponseDto;
import com.example.sismportal.service.InternshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/internships")
@RequiredArgsConstructor
public class AdminInternshipController {

    private final InternshipService internshipService;

    @PostMapping
    public ResponseEntity<InternshipResponseDto> create(@Valid @RequestBody InternshipRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(internshipService.createInternship(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipResponseDto> update(@PathVariable Long id,
                                                        @Valid @RequestBody InternshipRequestDto requestDto) {
        return ResponseEntity.ok(internshipService.updateInternship(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }
}
