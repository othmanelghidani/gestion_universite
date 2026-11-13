package com.univ.gest.repository;

import com.univ.gest.domain.entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
    Optional<TeacherProfile> findByUserEmail(String email);
}
