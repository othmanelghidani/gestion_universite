package com.univ.gest.domain.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_entity") // ✅ IMPORTANT : correspond à ta FK MySQL
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Si ta table a un autre nom de colonne, dis-moi, mais souvent c'est "name"
    @Column(nullable = false, length = 120)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<ModuleEntity> modules = new HashSet<>();

    public GroupEntity() {}

    public GroupEntity(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<ModuleEntity> getModules() { return modules; }
    public void setModules(Set<ModuleEntity> modules) { this.modules = modules; }
}
