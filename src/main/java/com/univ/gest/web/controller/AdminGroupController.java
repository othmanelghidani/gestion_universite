package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.GroupDto;
import com.univ.gest.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/groups")
public class AdminGroupController {

    private final GroupService groupService;

    public AdminGroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("groups", groupService.findAll());
        return "groups/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("groupDto", new GroupDto());
        model.addAttribute("mode", "create");
        return "groups/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("groupDto") GroupDto dto,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "groups/form";
        }

        groupService.create(dto);
        ra.addFlashAttribute("success", "Groupe créé avec succès");
        return "redirect:/admin/groups";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("groupDto", groupService.findById(id));
        model.addAttribute("mode", "edit");
        return "groups/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("groupDto") GroupDto dto,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "groups/form";
        }

        groupService.update(id, dto);
        ra.addFlashAttribute("success", "Groupe modifié avec succès");
        return "redirect:/admin/groups";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        groupService.delete(id);
        ra.addFlashAttribute("success", "Groupe supprimé");
        return "redirect:/admin/groups";
    }
}
