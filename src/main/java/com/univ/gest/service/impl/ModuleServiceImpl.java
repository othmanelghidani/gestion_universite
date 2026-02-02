package com.univ.gest.service.impl;

import com.univ.gest.domain.dto.ModuleDto;
import com.univ.gest.domain.entity.ModuleEntity;
import com.univ.gest.domain.entity.TeacherProfile;
import com.univ.gest.repository.ModuleRepository;
import com.univ.gest.repository.TeacherProfileRepository;
import com.univ.gest.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final TeacherProfileRepository teacherProfileRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, TeacherProfileRepository teacherProfileRepository) {
        this.moduleRepository = moduleRepository;
        this.teacherProfileRepository = teacherProfileRepository;
    }

    @Override
    public List<ModuleDto> findAll() {
        return moduleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ModuleDto findById(Long id) {
        ModuleEntity m = moduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable (id=" + id + ")"));
        return toDto(m);
    }

    @Override
    public void create(ModuleDto dto) {
        ModuleEntity m = new ModuleEntity();
        applyDto(m, dto);
        moduleRepository.save(m);
    }

    @Override
    public void update(Long id, ModuleDto dto) {
        ModuleEntity m = moduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable (id=" + id + ")"));
        applyDto(m, dto);
        moduleRepository.save(m);
    }

    @Override
    public void delete(Long id) {
        ModuleEntity m = moduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable (id=" + id + ")"));
        moduleRepository.delete(m);
    }

    private ModuleDto toDto(ModuleEntity m) {
        ModuleDto dto = new ModuleDto();
        dto.setId(m.getId());
        dto.setName(m.getName());
        dto.setFiliere(m.getFiliere());
        dto.setSemestre(m.getSemestre());
        dto.setVolumeHoraire(m.getVolumeHoraire());
        dto.setCompetencesRequises(m.getCompetencesRequises());

        if (m.getResponsibleTeacher() != null) {
            dto.setResponsibleTeacherId(m.getResponsibleTeacher().getId());

            // ✅ Email du responsable (TeacherProfile -> User -> Email)
            if (m.getResponsibleTeacher().getUser() != null) {
                dto.setResponsibleTeacherEmail(m.getResponsibleTeacher().getUser().getEmail());
            }
        }

        return dto;
    }

    private void applyDto(ModuleEntity m, ModuleDto dto) {
        m.setName(dto.getName());
        m.setFiliere(dto.getFiliere());
        m.setSemestre(dto.getSemestre());
        m.setVolumeHoraire(dto.getVolumeHoraire());
        m.setCompetencesRequises(dto.getCompetencesRequises());

        if (dto.getResponsibleTeacherId() == null) {
            m.setResponsibleTeacher(null);
        } else {
            TeacherProfile t = teacherProfileRepository.findById(dto.getResponsibleTeacherId())
                    .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable (id=" + dto.getResponsibleTeacherId() + ")"));
            m.setResponsibleTeacher(t);
        }
    }
}
