package com.univ.gest.service;

import com.univ.gest.domain.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> findAll();
    StudentDto findById(Long id);
    void create(StudentDto dto);
    void update(Long id, StudentDto dto);
    void delete(Long id);
}
