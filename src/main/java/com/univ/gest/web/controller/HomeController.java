package com.univ.gest.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "UniversityGest");
        model.addAttribute("message", "✅ Étape 1 OK : Spring Boot + Thymeleaf + MySQL configuré");
        return "home";
    }
}
