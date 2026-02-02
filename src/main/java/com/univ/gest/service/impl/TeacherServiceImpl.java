package com.univ.gest.service.impl;

import com.univ.gest.domain.dto.TeacherDto;
import com.univ.gest.domain.entity.Role;
import com.univ.gest.domain.entity.TeacherProfile;
import com.univ.gest.domain.entity.User;
import com.univ.gest.repository.RoleRepository;
import com.univ.gest.repository.TeacherProfileRepository;
import com.univ.gest.repository.UserRepository;
import com.univ.gest.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherProfileRepository teacherRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImpl(TeacherProfileRepository teacherRepo,
                              UserRepository userRepo,
                              RoleRepository roleRepo,
                              PasswordEncoder passwordEncoder) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<TeacherProfile> findAll() {
        return teacherRepo.findAll();
    }

    @Override
    public TeacherProfile findById(Long id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable : " + id));
    }

    @Override
    public TeacherProfile create(TeacherDto dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        Role teacherRole = roleRepo.findByNameIgnoreCase("ROLE_TEACHER")
                .orElseThrow(() -> new IllegalStateException("ROLE_TEACHER non trouvé en base"));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(teacherRole);
        user.setRoles(roles);

        user = userRepo.save(user);

        TeacherProfile tp = new TeacherProfile();
        tp.setGrade(dto.getGrade());
        tp.setSpecialite(dto.getSpecialite());
        tp.setCompetences(dto.getCompetences());
        tp.setUser(user);

        return teacherRepo.save(tp);
    }

    @Override
    public TeacherProfile update(Long id, TeacherDto dto) {
        TeacherProfile tp = findById(id);

        // email : modifiable si tu veux, sinon commente cette partie
        String newEmail = dto.getEmail();
        if (!tp.getUser().getEmail().equalsIgnoreCase(newEmail)) {
            if (userRepo.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email déjà utilisé");
            }
            tp.getUser().setEmail(newEmail);
        }

        // password optionnel en edit
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getPassword().length() < 6) {
                throw new IllegalArgumentException("Mot de passe : 6 caractères minimum");
            }
            tp.getUser().setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        tp.setGrade(dto.getGrade());
        tp.setSpecialite(dto.getSpecialite());
        tp.setCompetences(dto.getCompetences());

        return teacherRepo.save(tp);
    }

    @Override
    public void delete(Long id) {
        TeacherProfile tp = findById(id);
        Long userId = tp.getUser().getId();
        teacherRepo.delete(tp);
        userRepo.deleteById(userId);
    }
}
