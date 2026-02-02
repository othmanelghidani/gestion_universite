package com.univ.gest.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ModuleDto {

    private Long id;

    @NotBlank(message = "Nom obligatoire")
    @Size(max = 120, message = "Max 120 caractères")
    private String name;

    @NotBlank(message = "Filière obligatoire")
    @Size(max = 120, message = "Max 120 caractères")
    private String filiere;

    @NotBlank(message = "Semestre obligatoire")
    @Size(max = 30, message = "Max 30 caractères")
    private String semestre;

    @Min(value = 1, message = "Volume horaire doit être >= 1")
    @Max(value = 5000, message = "Volume horaire trop grand")
    private int volumeHoraire;

    @Size(max = 1000, message = "Max 1000 caractères")
    private String competencesRequises;

    // optionnel
    private Long responsibleTeacherId;

    // ✅ Champ pour affichage dans la liste (UI)
    private String responsibleTeacherEmail;

    public ModuleDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public int getVolumeHoraire() { return volumeHoraire; }
    public void setVolumeHoraire(int volumeHoraire) { this.volumeHoraire = volumeHoraire; }

    public String getCompetencesRequises() { return competencesRequises; }
    public void setCompetencesRequises(String competencesRequises) { this.competencesRequises = competencesRequises; }

    public Long getResponsibleTeacherId() { return responsibleTeacherId; }
    public void setResponsibleTeacherId(Long responsibleTeacherId) { this.responsibleTeacherId = responsibleTeacherId; }

    public String getResponsibleTeacherEmail() { return responsibleTeacherEmail; }
    public void setResponsibleTeacherEmail(String responsibleTeacherEmail) { this.responsibleTeacherEmail = responsibleTeacherEmail; }
}
