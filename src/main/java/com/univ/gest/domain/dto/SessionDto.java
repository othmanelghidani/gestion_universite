package com.univ.gest.domain.dto;

import jakarta.validation.constraints.*;

public class SessionDto {

    private Long id;

    @Min(value = 1, message = "Jour invalide")
    @Max(value = 7, message = "Jour invalide")
    private int dayOfWeek;

    @NotBlank(message = "Heure début obligatoire (HH:MM)")
    private String startTime;

    @NotBlank(message = "Heure fin obligatoire (HH:MM)")
    private String endTime;

    @NotNull(message = "Module obligatoire")
    private Long moduleId;

    @NotNull(message = "Groupe obligatoire")
    private Long groupId;

    @NotNull(message = "Enseignant obligatoire")
    private Long teacherId;

    @NotNull(message = "Salle obligatoire")
    private Long roomId;

    // ===== Champs affichage (LISTE) =====
    // (ne participent pas au formulaire, donc safe)
    private String moduleName;
    private String groupName;
    private String roomName;

    private String teacherEmail;
    private String teacherGrade;

    public SessionDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }

    public String getTeacherGrade() { return teacherGrade; }
    public void setTeacherGrade(String teacherGrade) { this.teacherGrade = teacherGrade; }
}
