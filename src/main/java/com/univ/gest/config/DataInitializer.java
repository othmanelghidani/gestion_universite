package com.univ.gest.config;

import com.univ.gest.domain.entity.Role;
import com.univ.gest.domain.entity.User;
import com.univ.gest.repository.RoleRepository;
import com.univ.gest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepo.findByNameIgnoreCase("ROLE_ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

        roleRepo.findByNameIgnoreCase("ROLE_TEACHER")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_TEACHER")));

        roleRepo.findByNameIgnoreCase("ROLE_STUDENT")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_STUDENT")));

        String adminEmail = "admin@univ.local";
        if (!userRepo.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(encoder.encode("admin123"));
            admin.setEnabled(true);

            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepo.save(admin);
        }
    }
}
