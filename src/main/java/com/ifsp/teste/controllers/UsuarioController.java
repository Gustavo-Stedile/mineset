package com.ifsp.teste.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.teste.TesteApplication;
import com.ifsp.teste.models.Usuario;
import com.ifsp.teste.services.UsuarioService;
import com.ifsp.teste.utils.BodyParser;

@Controller
public class UsuarioController {   

    @Autowired
    public UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public String signup(
        @RequestParam String nome, 
        @RequestParam String email,
        @RequestParam String senha
        
    ) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setSenha(senha);
        u.setEmail(email);

        usuarioService.criar(u);
        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(
        Model model,
        @RequestParam String email,
        @RequestParam String senha
    ) {
        Usuario u = usuarioService.autenticar(email, senha);
        
        if (u == null) {
            model.addAttribute("mensagem", "email ou senha incorretos");
            return "/login";
        } 

        //TODO: criar a home!
        return "redirect:/home";
    }
    
}
