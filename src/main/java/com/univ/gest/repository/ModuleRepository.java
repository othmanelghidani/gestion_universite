package com.univ.gest.repository;

import com.univ.gest.domain.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {

    // ✅ Charge module + groups en une requête (évite LazyInitializationException)
    @Query("SELECT m FROM ModuleEntity m LEFT JOIN FETCH m.groups WHERE m.id = :id")
    Optional<ModuleEntity> findByIdWithGroups(@Param("id") Long id);
}
