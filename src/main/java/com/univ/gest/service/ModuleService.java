package com.univ.gest.service;

import com.univ.gest.domain.dto.ModuleDto;

import java.util.List;

public interface ModuleService {
    List<ModuleDto> findAll();
    ModuleDto findById(Long id);
    void create(ModuleDto dto);
    void update(Long id, ModuleDto dto);
    void delete(Long id);
}
