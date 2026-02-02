package com.univ.gest.repository;

import com.univ.gest.domain.entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {

    // Trouver le teacher profile via email du user lié
    Optional<TeacherProfile> findByUserEmail(String email);

    // Optionnel (utile parfois)
    Optional<TeacherProfile> findByUserId(Long userId);
}
