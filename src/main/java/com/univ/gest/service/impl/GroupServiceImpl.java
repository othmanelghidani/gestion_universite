package com.univ.gest.service.impl;

import com.univ.gest.domain.dto.GroupDto;
import com.univ.gest.domain.entity.GroupEntity;
import com.univ.gest.repository.GroupRepository;
import com.univ.gest.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupDto> findAll() {
        return groupRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public GroupDto findById(Long id) {
        GroupEntity g = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable (id=" + id + ")"));
        return toDto(g);
    }

    @Override
    public void create(GroupDto dto) {
        GroupEntity g = new GroupEntity();
        g.setName(dto.getName());
        groupRepository.save(g);
    }

    @Override
    public void update(Long id, GroupDto dto) {
        GroupEntity g = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable (id=" + id + ")"));
        g.setName(dto.getName());
        groupRepository.save(g);
    }

    @Override
    public void delete(Long id) {
        GroupEntity g = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable (id=" + id + ")"));
        groupRepository.delete(g);
    }

    private GroupDto toDto(GroupEntity g) {
        GroupDto dto = new GroupDto();
        dto.setId(g.getId());
        dto.setName(g.getName());
        return dto;
    }
}
