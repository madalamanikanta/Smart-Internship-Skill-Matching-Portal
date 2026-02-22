package com.example.sismportal.security;

import com.example.sismportal.model.Admin;
import com.example.sismportal.model.Student;
import com.example.sismportal.repository.AdminRepository;
import com.example.sismportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(username).orElse(null);
        if (student != null) {
            return new User(student.getEmail(), student.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new User(admin.getUsername(), admin.getPassword(),
                List.of(new SimpleGrantedAuthority(admin.getRole().name())));
    }
}
