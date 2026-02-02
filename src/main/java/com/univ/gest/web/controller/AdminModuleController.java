package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.ModuleDto;
import com.univ.gest.repository.TeacherProfileRepository;
import com.univ.gest.service.ModuleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/modules")
public class AdminModuleController {

    private final ModuleService moduleService;
    private final TeacherProfileRepository teacherProfileRepository;

    public AdminModuleController(ModuleService moduleService,
                                 TeacherProfileRepository teacherProfileRepository) {
        this.moduleService = moduleService;
        this.teacherProfileRepository = teacherProfileRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("modules", moduleService.findAll());
        return "modules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("moduleDto", new ModuleDto());
        model.addAttribute("teachers", teacherProfileRepository.findAll());
        model.addAttribute("mode", "create");
        return "modules/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("moduleDto") ModuleDto dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        if (br.hasErrors()) {
            model.addAttribute("teachers", teacherProfileRepository.findAll());
            model.addAttribute("mode", "create");
            return "modules/form";
        }

        try {
            moduleService.create(dto);
            ra.addFlashAttribute("success", "Module créé avec succès");
            return "redirect:/admin/modules";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("teachers", teacherProfileRepository.findAll());
            model.addAttribute("mode", "create");
            model.addAttribute("error", ex.getMessage());
            return "modules/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ModuleDto dto = moduleService.findById(id);
        model.addAttribute("moduleDto", dto);
        model.addAttribute("teachers", teacherProfileRepository.findAll());
        model.addAttribute("mode", "edit");
        return "modules/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("moduleDto") ModuleDto dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {

        if (br.hasErrors()) {
            model.addAttribute("teachers", teacherProfileRepository.findAll());
            model.addAttribute("mode", "edit");
            return "modules/form";
        }

        try {
            moduleService.update(id, dto);
            ra.addFlashAttribute("success", "Module modifié avec succès");
            return "redirect:/admin/modules";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("teachers", teacherProfileRepository.findAll());
            model.addAttribute("mode", "edit");
            model.addAttribute("error", ex.getMessage());
            return "modules/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        moduleService.delete(id);
        ra.addFlashAttribute("success", "Module supprimé");
        return "redirect:/admin/modules";
    }
}
