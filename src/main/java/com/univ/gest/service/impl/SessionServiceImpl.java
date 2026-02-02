package com.univ.gest.service.impl;

import com.univ.gest.domain.dto.SessionDto;
import com.univ.gest.domain.entity.*;
import com.univ.gest.repository.*;
import com.univ.gest.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final ModuleRepository moduleRepository;
    private final GroupRepository groupRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final RoomRepository roomRepository;

    public SessionServiceImpl(SessionRepository sessionRepository,
                              ModuleRepository moduleRepository,
                              GroupRepository groupRepository,
                              TeacherProfileRepository teacherProfileRepository,
                              RoomRepository roomRepository) {
        this.sessionRepository = sessionRepository;
        this.moduleRepository = moduleRepository;
        this.groupRepository = groupRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<SessionDto> findAll() {
        return sessionRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public SessionDto findById(Long id) {
        SessionEntity s = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Séance introuvable (id=" + id + ")"));
        return toDto(s);
    }

    @Override
    public void create(SessionDto dto) {
        SessionEntity s = new SessionEntity();
        applyDto(s, dto, null);
        sessionRepository.save(s);
    }

    @Override
    public void update(Long id, SessionDto dto) {
        SessionEntity s = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Séance introuvable (id=" + id + ")"));
        applyDto(s, dto, id);
        sessionRepository.save(s);
    }

    @Override
    public void delete(Long id) {
        SessionEntity s = sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Séance introuvable (id=" + id + ")"));
        sessionRepository.delete(s);
    }

    private SessionDto toDto(SessionEntity s) {
        SessionDto dto = new SessionDto();
        dto.setId(s.getId());
        dto.setDayOfWeek(s.getDayOfWeek());
        dto.setStartTime(s.getStartTime().toString());
        dto.setEndTime(s.getEndTime().toString());
        dto.setModuleId(s.getModule().getId());
        dto.setGroupId(s.getGroup().getId());
        dto.setTeacherId(s.getTeacher().getId());
        dto.setRoomId(s.getRoom().getId());
        return dto;
    }

    private void applyDto(SessionEntity s, SessionDto dto, Long excludeId) {

        LocalTime start;
        LocalTime end;

        try {
            start = LocalTime.parse(dto.getStartTime());
            end = LocalTime.parse(dto.getEndTime());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Format heure invalide. Utilise HH:MM (ex: 08:30)");
        }

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Heure début doit être avant l’heure fin");
        }

        int day = dto.getDayOfWeek();

        ModuleEntity module = moduleRepository.findById(dto.getModuleId())
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable"));
        GroupEntity group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Groupe introuvable"));
        TeacherProfile teacher = teacherProfileRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Salle introuvable"));

        if (!sessionRepository.findRoomConflicts(day, room.getId(), start, end, excludeId).isEmpty()) {
            throw new IllegalArgumentException("Conflit: la salle est déjà occupée sur ce créneau");
        }
        if (!sessionRepository.findTeacherConflicts(day, teacher.getId(), start, end, excludeId).isEmpty()) {
            throw new IllegalArgumentException("Conflit: l’enseignant a déjà une séance sur ce créneau");
        }
        if (!sessionRepository.findGroupConflicts(day, group.getId(), start, end, excludeId).isEmpty()) {
            throw new IllegalArgumentException("Conflit: le groupe a déjà une séance sur ce créneau");
        }

        s.setDayOfWeek(day);
        s.setStartTime(start);
        s.setEndTime(end);
        s.setModule(module);
        s.setGroup(group);
        s.setTeacher(teacher);
        s.setRoom(room);
    }
}
