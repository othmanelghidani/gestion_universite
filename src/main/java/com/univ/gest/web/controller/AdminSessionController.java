package com.univ.gest.web.controller;

import com.univ.gest.domain.dto.SessionDto;
import com.univ.gest.repository.GroupRepository;
import com.univ.gest.repository.ModuleRepository;
import com.univ.gest.repository.RoomRepository;
import com.univ.gest.repository.TeacherProfileRepository;
import com.univ.gest.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/sessions")
public class AdminSessionController {

    private final SessionService sessionService;
    private final ModuleRepository moduleRepository;
    private final GroupRepository groupRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final RoomRepository roomRepository;

    public AdminSessionController(SessionService sessionService,
                                  ModuleRepository moduleRepository,
                                  GroupRepository groupRepository,
                                  TeacherProfileRepository teacherProfileRepository,
                                  RoomRepository roomRepository) {
        this.sessionService = sessionService;
        this.moduleRepository = moduleRepository;
        this.groupRepository = groupRepository;
        this.teacherProfileRepository = teacherProfileRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sessions", sessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("sessionDto", new SessionDto());
        loadRefs(model);
        model.addAttribute("mode", "create");
        return "sessions/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("sessionDto") SessionDto dto,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (br.hasErrors()) {
            loadRefs(model);
            model.addAttribute("mode", "create");
            return "sessions/form";
        }

        try {
            sessionService.create(dto);
            ra.addFlashAttribute("success", "Séance créée avec succès");
            return "redirect:/admin/sessions";
        } catch (IllegalArgumentException ex) {
            loadRefs(model);
            model.addAttribute("mode", "create");
            model.addAttribute("error", ex.getMessage());
            return "sessions/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("sessionDto", sessionService.findById(id));
        loadRefs(model);
        model.addAttribute("mode", "edit");
        return "sessions/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("sessionDto") SessionDto dto,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (br.hasErrors()) {
            loadRefs(model);
            model.addAttribute("mode", "edit");
            return "sessions/form";
        }

        try {
            sessionService.update(id, dto);
            ra.addFlashAttribute("success", "Séance modifiée avec succès");
            return "redirect:/admin/sessions";
        } catch (IllegalArgumentException ex) {
            loadRefs(model);
            model.addAttribute("mode", "edit");
            model.addAttribute("error", ex.getMessage());
            return "sessions/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        sessionService.delete(id);
        ra.addFlashAttribute("success", "Séance supprimée");
        return "redirect:/admin/sessions";
    }

    private void loadRefs(Model model) {
        model.addAttribute("modules", moduleRepository.findAll());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("teachers", teacherProfileRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
    }
}
