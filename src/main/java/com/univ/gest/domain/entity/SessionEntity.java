package com.univ.gest.domain.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "session_entity") // ✅ correspond à ta DB
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity module;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherProfile teacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public SessionEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public ModuleEntity getModule() { return module; }
    public void setModule(ModuleEntity module) { this.module = module; }

    public GroupEntity getGroup() { return group; }
    public void setGroup(GroupEntity group) { this.group = group; }

    public TeacherProfile getTeacher() { return teacher; }
    public void setTeacher(TeacherProfile teacher) { this.teacher = teacher; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
}
