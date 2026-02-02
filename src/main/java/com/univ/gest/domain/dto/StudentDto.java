package com.univ.gest.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentDto {

    private Long id; // id du StudentProfile

    @Email
    @NotBlank(message = "Email obligatoire")
    private String email;

    @NotBlank(message = "Mot de passe obligatoire")
    @Size(min = 6, message = "Mot de passe : 6 caractères minimum")
    private String password;

    @NotBlank(message = "Filière obligatoire")
    private String filiere;

    @NotBlank(message = "Niveau obligatoire")
    private String niveau;

    @NotBlank(message = "Groupe obligatoire")
    private String groupe;

    private String competences;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getGroupe() { return groupe; }
    public void setGroupe(String groupe) { this.groupe = groupe; }

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }
}
