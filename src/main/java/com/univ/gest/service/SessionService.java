package com.univ.gest.service;

import com.univ.gest.domain.dto.SessionDto;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAll();
    SessionDto findById(Long id);
    void create(SessionDto dto);
    void update(Long id, SessionDto dto);
    void delete(Long id);
}
