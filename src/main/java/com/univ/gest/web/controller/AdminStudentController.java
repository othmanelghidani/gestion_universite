package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.StudentDto;
import com.univ.gest.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/students")
public class AdminStudentController {

    private final StudentService studentService;

    public AdminStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        model.addAttribute("mode", "create");
        return "students/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("studentDto") StudentDto dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "students/form";
        }
        try {
            studentService.create(dto);
            ra.addFlashAttribute("success", "Étudiant créé avec succès");
            return "redirect:/admin/students";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("mode", "create");
            model.addAttribute("error", ex.getMessage());
            return "students/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        StudentDto dto = studentService.findById(id);
        // password vide => si l’admin laisse vide, on ne change pas le mot de passe
        dto.setPassword("");
        model.addAttribute("studentDto", dto);
        model.addAttribute("mode", "edit");
        return "students/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("studentDto") StudentDto dto,
                         BindingResult br,
                         RedirectAttributes ra,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "students/form";
        }
        try {
            studentService.update(id, dto);
            ra.addFlashAttribute("success", "Étudiant modifié avec succès");
            return "redirect:/admin/students";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("mode", "edit");
            model.addAttribute("error", ex.getMessage());
            return "students/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        studentService.delete(id);
        ra.addFlashAttribute("success", "Étudiant supprimé");
        return "redirect:/admin/students";
    }
}
