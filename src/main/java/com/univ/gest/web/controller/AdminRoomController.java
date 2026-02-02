package com.univ.gest.web.controller;

import com.univ.gest.domain.entity.Room;
import com.univ.gest.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/rooms")
public class AdminRoomController {

    private final RoomRepository roomRepository;

    public AdminRoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("room", new Room());
        return "rooms/form";
    }

    @PostMapping
    public String save(@ModelAttribute Room room, RedirectAttributes ra) {
        roomRepository.save(room);
        ra.addFlashAttribute("success", "Salle enregistrée");
        return "redirect:/admin/rooms";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        roomRepository.deleteById(id);
        ra.addFlashAttribute("success", "Salle supprimée");
        return "redirect:/admin/rooms";
    }
}
