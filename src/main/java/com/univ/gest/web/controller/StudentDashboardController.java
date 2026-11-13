package com.univ.gest.web.controller;

import com.univ.gest.domain.entity.GroupEntity;
import com.univ.gest.domain.entity.SessionEntity;
import com.univ.gest.domain.entity.StudentProfile;
import com.univ.gest.repository.GroupRepository;
import com.univ.gest.repository.SessionRepository;
import com.univ.gest.repository.StudentProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class StudentDashboardController {

    private final StudentProfileRepository studentRepo;
    private final GroupRepository groupRepo;
    private final SessionRepository sessionRepo;

    public StudentDashboardController(StudentProfileRepository studentRepo,
                                      GroupRepository groupRepo,
                                      SessionRepository sessionRepo) {
        this.studentRepo = studentRepo;
        this.groupRepo = groupRepo;
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/student/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String email = auth.getName();

        StudentProfile student = studentRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        GroupEntity group = groupRepo.findByNameIgnoreCase(student.getGroupe())
                .orElseThrow(() -> new RuntimeException("Groupe introuvable : " + student.getGroupe()));

        List<SessionEntity> sessions =
                sessionRepo.findByGroup_IdOrderByDayOfWeekAscStartTimeAsc(group.getId());

        model.addAttribute("email", email);
        model.addAttribute("student", student);
        model.addAttribute("group", group);
        model.addAttribute("sessions", sessions);
        model.addAttribute("days", Map.of(
                1, "Lundi", 2, "Mardi", 3, "Mercredi",
                4, "Jeudi", 5, "Vendredi", 6, "Samedi", 7, "Dimanche"
        ));

        return "student/dashboard";
    }
}
