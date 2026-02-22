package com.example.sismportal.service.impl;

import com.example.sismportal.dto.SkillDto;
import com.example.sismportal.repository.SkillRepository;
import com.example.sismportal.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SkillDto> getAllSkills() {
        return skillRepository.findAll().stream().map(DtoMapper::toSkillDto).toList();
    }
}
