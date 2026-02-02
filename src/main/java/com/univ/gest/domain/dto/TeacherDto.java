package com.univ.gest.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TeacherDto {

    private Long id; // teacherProfileId (pour edit)

    @NotBlank
    @Email
    private String email;

    // obligatoire en création, optionnel en modification (on gère dans le controller)
    @Size(min = 6, message = "Mot de passe : 6 caractères minimum")
    private String password;

    @NotBlank(message = "Grade obligatoire")
    private String grade;

    @NotBlank(message = "Spécialité obligatoire")
    private String specialite;

    private String competences; // optionnel

    public TeacherDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }
}
