package com.univ.gest.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GroupDto {

    private Long id;

    @NotBlank(message = "Nom obligatoire")
    @Size(max = 120, message = "Max 120 caractères")
    private String name;

    public GroupDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
