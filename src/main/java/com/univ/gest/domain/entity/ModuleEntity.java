package com.univ.gest.domain.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "module_entity")
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 120)
    private String filiere;

    @Column(nullable = false, length = 50)
    private String semestre;

    @Column(nullable = false)
    private Integer volumeHoraire;

    @Column(length = 500)
    private String competencesRequises;

    @ManyToOne
    @JoinColumn(name = "responsible_teacher_id")
    private TeacherProfile responsibleTeacher;

    @ManyToMany
    @JoinTable(
            name = "module_groups",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<GroupEntity> groups = new HashSet<>();

    public ModuleEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public Integer getVolumeHoraire() { return volumeHoraire; }
    public void setVolumeHoraire(Integer volumeHoraire) { this.volumeHoraire = volumeHoraire; }

    public String getCompetencesRequises() { return competencesRequises; }
    public void setCompetencesRequises(String competencesRequises) { this.competencesRequises = competencesRequises; }

    public TeacherProfile getResponsibleTeacher() { return responsibleTeacher; }
    public void setResponsibleTeacher(TeacherProfile responsibleTeacher) { this.responsibleTeacher = responsibleTeacher; }

    public Set<GroupEntity> getGroups() { return groups; }
    public void setGroups(Set<GroupEntity> groups) { this.groups = groups; }
}
