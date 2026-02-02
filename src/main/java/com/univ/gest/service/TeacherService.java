package com.univ.gest.service;

import com.univ.gest.domain.dto.TeacherDto;
import com.univ.gest.domain.entity.TeacherProfile;

import java.util.List;

public interface TeacherService {
    List<TeacherProfile> findAll();
    TeacherProfile findById(Long id);
    TeacherProfile create(TeacherDto dto);
    TeacherProfile update(Long id, TeacherDto dto);
    void delete(Long id);
}
