package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.TeacherDto;
import com.univ.gest.domain.entity.TeacherProfile;
import com.univ.gest.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/teachers")
public class AdminTeacherController {

    private final TeacherService teacherService;

    public AdminTeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("teacherDto", new TeacherDto());
        model.addAttribute("mode", "create");
        return "teachers/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("teacherDto") TeacherDto dto,
                         BindingResult br,
                         Model model) {
        // mot de passe obligatoire en création
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            br.rejectValue("password", "NotBlank", "Mot de passe obligatoire");
        }

        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "teachers/form";
        }

        try {
            teacherService.create(dto);
            return "redirect:/admin/teachers";
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("mode", "create");
            model.addAttribute("errorMessage", ex.getMessage());
            return "teachers/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        TeacherProfile tp = teacherService.findById(id);

        TeacherDto dto = new TeacherDto();
        dto.setId(tp.getId());
        dto.setEmail(tp.getUser().getEmail());
        dto.setGrade(tp.getGrade());
        dto.setSpecialite(tp.getSpecialite());
        dto.setCompetences(tp.getCompetences());

        model.addAttribute("teacherDto", dto);
        model.addAttribute("mode", "edit");
        return "teachers/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("teacherDto") TeacherDto dto,
                         BindingResult br,
                         Model model) {
        // en edit : password optionnel => on enlève l’erreur size si vide
        if (dto.getPassword() != null && dto.getPassword().isBlank()) {
            dto.setPassword(null);
        }

        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "teachers/form";
        }

        try {
            teacherService.update(id, dto);
            return "redirect:/admin/teachers";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("mode", "edit");
            model.addAttribute("errorMessage", ex.getMessage());
            return "teachers/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        teacherService.delete(id);
        return "redirect:/admin/teachers";
    }
}
