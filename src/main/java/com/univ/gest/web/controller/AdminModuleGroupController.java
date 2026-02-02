package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.ModuleAssignGroupsDto;
import com.univ.gest.domain.entity.GroupEntity;
import com.univ.gest.domain.entity.ModuleEntity;
import com.univ.gest.repository.GroupRepository;
import com.univ.gest.repository.ModuleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/modules")
public class AdminModuleGroupController {

    private final ModuleRepository moduleRepository;
    private final GroupRepository groupRepository;

    public AdminModuleGroupController(ModuleRepository moduleRepository, GroupRepository groupRepository) {
        this.moduleRepository = moduleRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/{id}/groups")
    public String form(@PathVariable Long id, Model model) {

        // ✅ IMPORTANT: on charge le module AVEC ses groupes
        ModuleEntity module = moduleRepository.findByIdWithGroups(id)
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable (id=" + id + ")"));

        ModuleAssignGroupsDto dto = new ModuleAssignGroupsDto();
        dto.setModuleId(module.getId());
        dto.setGroupIds(module.getGroups().stream().map(GroupEntity::getId).toList());

        model.addAttribute("module", module);
        model.addAttribute("dto", dto);
        model.addAttribute("groups", groupRepository.findAll());

        return "modules/assign-groups";
    }

    @PostMapping("/{id}/groups")
    public String save(@PathVariable Long id,
                       @ModelAttribute("dto") ModuleAssignGroupsDto dto,
                       RedirectAttributes ra) {

        ModuleEntity module = moduleRepository.findByIdWithGroups(id)
                .orElseThrow(() -> new IllegalArgumentException("Module introuvable (id=" + id + ")"));

        List<Long> ids = dto.getGroupIds();
        if (ids == null) ids = List.of();

        List<GroupEntity> selected = groupRepository.findAllById(ids);
        Set<GroupEntity> set = new HashSet<>(selected);

        module.setGroups(set);
        moduleRepository.save(module);

        ra.addFlashAttribute("success", "Groupes affectés avec succès");
        return "redirect:/admin/modules";
    }
}
