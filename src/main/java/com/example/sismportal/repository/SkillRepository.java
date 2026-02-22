package com.example.sismportal.repository;

import com.example.sismportal.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillNameIgnoreCase(String skillName);
}
