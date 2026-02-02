package com.univ.gest.repository;

import com.univ.gest.domain.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    // Trouver le student profile via email du user lié
    Optional<StudentProfile> findByUserEmail(String email);

    // Optionnel
    Optional<StudentProfile> findByUserId(Long userId);
}
