package com.example.sismportal.repository;

import com.example.sismportal.model.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT DISTINCT i FROM Internship i JOIN i.requiredSkills s WHERE LOWER(s.skillName) IN :skillNames")
    Page<Internship> findBySkillNames(Set<String> skillNames, Pageable pageable);
}
