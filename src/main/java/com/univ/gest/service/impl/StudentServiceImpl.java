package com.univ.gest.service.impl;

import com.univ.gest.domain.dto.StudentDto;
import com.univ.gest.domain.entity.Role;
import com.univ.gest.domain.entity.StudentProfile;
import com.univ.gest.domain.entity.User;
import com.univ.gest.repository.RoleRepository;
import com.univ.gest.repository.StudentProfileRepository;
import com.univ.gest.repository.UserRepository;
import com.univ.gest.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentProfileRepository studentRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentProfileRepository studentRepo,
                              UserRepository userRepo,
                              RoleRepository roleRepo,
                              PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<StudentDto> findAll() {
        return studentRepo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public StudentDto findById(Long id) {
        StudentProfile sp = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable"));
        return toDto(sp);
    }

    @Override
    public void create(StudentDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        Role studentRole = roleRepo.findByName("ROLE_STUDENT")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_STUDENT")));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.getRoles().add(studentRole);

        StudentProfile sp = new StudentProfile();
        sp.setFiliere(dto.getFiliere());
        sp.setNiveau(dto.getNiveau());
        sp.setGroupe(dto.getGroupe());
        sp.setCompetences(dto.getCompetences());

        sp.setUser(user);
        user.setStudentProfile(sp);

        userRepo.save(user); // cascade crée student_profile
    }

    @Override
    public void update(Long id, StudentDto dto) {
        StudentProfile sp = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable"));

        User user = sp.getUser();

        // email modifié ? vérifier unicité
        if (!user.getEmail().equals(dto.getEmail())
                && userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        user.setEmail(dto.getEmail());

        // si password non vide, on le met à jour
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        sp.setFiliere(dto.getFiliere());
        sp.setNiveau(dto.getNiveau());
        sp.setGroupe(dto.getGroupe());
        sp.setCompetences(dto.getCompetences());

        userRepo.save(user);
        studentRepo.save(sp);
    }

    @Override
    public void delete(Long id) {
        StudentProfile sp = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable"));
        // supprimer le user supprime le profile (orphanRemoval/cascade)
        userRepo.delete(sp.getUser());
    }

    private StudentDto toDto(StudentProfile sp) {
        StudentDto dto = new StudentDto();
        dto.setId(sp.getId());
        dto.setEmail(sp.getUser().getEmail());
        dto.setFiliere(sp.getFiliere());
        dto.setNiveau(sp.getNiveau());
        dto.setGroupe(sp.getGroupe());
        dto.setCompetences(sp.getCompetences());
        dto.setPassword(""); // on n’affiche jamais le hash
        return dto;
    }
}
