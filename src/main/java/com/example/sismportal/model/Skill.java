package com.example.sismportal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String skillName;

    @ManyToMany(mappedBy = "skills")
    @Builder.Default
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "requiredSkills")
    @Builder.Default
    private Set<Internship> internships = new HashSet<>();
}
