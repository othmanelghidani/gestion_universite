package com.univ.gest.web.controller;

import com.univ.gest.domain.entity.SessionEntity;
import com.univ.gest.domain.entity.TeacherProfile;
import com.univ.gest.repository.SessionRepository;
import com.univ.gest.repository.TeacherProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class TeacherDashboardController {

    private final TeacherProfileRepository teacherRepo;
    private final SessionRepository sessionRepo;

    public TeacherDashboardController(TeacherProfileRepository teacherRepo, SessionRepository sessionRepo) {
        this.teacherRepo = teacherRepo;
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/teacher/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String email = auth.getName();

        TeacherProfile teacher = teacherRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher introuvable"));

        List<SessionEntity> sessions =
                sessionRepo.findByTeacher_IdOrderByDayOfWeekAscStartTimeAsc(teacher.getId());

        model.addAttribute("email", email);
        model.addAttribute("teacher", teacher);
        model.addAttribute("sessions", sessions);
        model.addAttribute("days", Map.of(
                1, "Lundi", 2, "Mardi", 3, "Mercredi",
                4, "Jeudi", 5, "Vendredi", 6, "Samedi", 7, "Dimanche"
        ));

        return "teacher/dashboard";
    }
}
