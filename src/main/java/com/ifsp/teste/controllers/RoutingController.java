package com.ifsp.teste.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutingController {
    
    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/login") 
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/cadastro") 
    public String cadastro(Model model) {
        return "cadastro";
    }

    @GetMapping("/home") 
    public String home() {
        return "home";
    }
}