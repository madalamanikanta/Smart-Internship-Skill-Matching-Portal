package com.example.sismportal.service.impl;

import com.example.sismportal.dto.InternshipRequestDto;
import com.example.sismportal.dto.InternshipResponseDto;
import com.example.sismportal.exception.ResourceNotFoundException;
import com.example.sismportal.model.Admin;
import com.example.sismportal.model.Internship;
import com.example.sismportal.model.Skill;
import com.example.sismportal.repository.AdminRepository;
import com.example.sismportal.repository.InternshipRepository;
import com.example.sismportal.repository.SkillRepository;
import com.example.sismportal.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;
    private final SkillRepository skillRepository;
    private final AdminRepository adminRepository;

    @Override
    @Transactional
    public InternshipResponseDto createInternship(InternshipRequestDto requestDto) {
        Internship internship = buildInternship(new Internship(), requestDto);
        return DtoMapper.toInternshipResponse(internshipRepository.save(internship));
    }

    @Override
    @Transactional
    public InternshipResponseDto updateInternship(Long internshipId, InternshipRequestDto requestDto) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found for id: " + internshipId));
        internship = buildInternship(internship, requestDto);
        return DtoMapper.toInternshipResponse(internshipRepository.save(internship));
    }

    @Override
    @Transactional
    public void deleteInternship(Long internshipId) {
        if (!internshipRepository.existsById(internshipId)) {
            throw new ResourceNotFoundException("Internship not found for id: " + internshipId);
        }
        internshipRepository.deleteById(internshipId);
    }

    @Override
    @Transactional(readOnly = true)
    public InternshipResponseDto getInternshipById(Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found for id: " + internshipId));
        return DtoMapper.toInternshipResponse(internship);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InternshipResponseDto> getAllInternships(int page, int size, String sortBy, String direction) {
        Pageable pageable = getPageable(page, size, sortBy, direction);
        return internshipRepository.findAll(pageable).map(DtoMapper::toInternshipResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InternshipResponseDto> filterBySkills(Set<String> skills, int page, int size, String sortBy, String direction) {
        Pageable pageable = getPageable(page, size, sortBy, direction);
        Set<String> normalizedSkills = skills.stream().map(s -> s.toLowerCase(Locale.ROOT)).collect(Collectors.toSet());
        return internshipRepository.findBySkillNames(normalizedSkills, pageable).map(DtoMapper::toInternshipResponse);
    }

    private Internship buildInternship(Internship internship, InternshipRequestDto requestDto) {
        Set<Skill> requiredSkills = requestDto.getRequiredSkillIds().stream()
                .map(this::getSkill)
                .collect(Collectors.toSet());
        Admin admin = adminRepository.findById(requestDto.getAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found for id: " + requestDto.getAdminId()));

        internship.setTitle(requestDto.getTitle());
        internship.setOrganization(requestDto.getOrganization());
        internship.setRequiredSkills(requiredSkills);
        internship.setSource(requestDto.getSource());
        internship.setExternalLink(requestDto.getExternalLink());
        internship.setCreatedBy(admin);
        return internship;
    }

    private Skill getSkill(Long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found for id: " + skillId));
    }

    private Pageable getPageable(int page, int size, String sortBy, String direction) {
        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}
