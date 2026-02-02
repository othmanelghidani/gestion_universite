package com.univ.gest.service;

import com.univ.gest.domain.dto.GroupDto;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAll();
    GroupDto findById(Long id);
    void create(GroupDto dto);
    void update(Long id, GroupDto dto);
    void delete(Long id);
}
