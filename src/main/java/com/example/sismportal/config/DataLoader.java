package com.example.sismportal.config;

import com.example.sismportal.model.Admin;
import com.example.sismportal.model.Internship;
import com.example.sismportal.model.Role;
import com.example.sismportal.model.Skill;
import com.example.sismportal.model.Student;
import com.example.sismportal.repository.AdminRepository;
import com.example.sismportal.repository.InternshipRepository;
import com.example.sismportal.repository.SkillRepository;
import com.example.sismportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SkillRepository skillRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final InternshipRepository internshipRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.count() > 0 || studentRepository.count() > 0) {
            return;
        }

        Skill java = skillRepository.save(Skill.builder().skillName("Java").build());
        Skill spring = skillRepository.save(Skill.builder().skillName("Spring Boot").build());
        Skill sql = skillRepository.save(Skill.builder().skillName("SQL").build());

        Admin admin = adminRepository.save(Admin.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ROLE_ADMIN)
                .build());

        studentRepository.save(Student.builder()
                .name("Alice Student")
                .email("alice@student.com")
                .password(passwordEncoder.encode("alice123"))
                .skills(Set.of(java, sql))
                .build());

        internshipRepository.save(Internship.builder()
                .title("Backend Intern")
                .organization("Tech Campus Labs")
                .requiredSkills(Set.of(java, spring, sql))
                .source("University Career Cell")
                .externalLink("https://example.com/backend-intern")
                .createdBy(admin)
                .build());

        internshipRepository.save(Internship.builder()
                .title("Java Developer Intern")
                .organization("Academic Innovators")
                .requiredSkills(Set.of(java))
                .source("Company Website")
                .externalLink("https://example.com/java-intern")
                .createdBy(admin)
                .build());
    }
}
