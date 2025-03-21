package com.ifsp.teste.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ifsp.services.UsuarioService;
import com.ifsp.teste.models.Usuario;
import com.ifsp.teste.utils.BodyParser;



@Controller
public class UsuarioController {   

    @Autowired
    @Qualifier("UsuarioServiceImpl")
    public UsuarioService usuarioService;

    @GetMapping("/signup")
    public String viewSignup() {
        return "signup";
    }    

    @PostMapping("/cadastro")
    public String signup(@RequestBody String body) {
        Map<String, String> bodyParsed = BodyParser.parse(body);

        Usuario u = new Usuario();
        u.setNome(bodyParsed.get("nome"));
        u.setSenha(bodyParsed.get("senha"));
        u.setEmail(bodyParsed.get("email"));

        usuarioService.criar(u);
        return "redirect:/index";
    }


    @PostMapping("/login")
    public String login(@RequestBody String body) {
        Map<String, String> bodyParsed = BodyParser.parse(body);
        Usuario u = usuarioService.autenticar(bodyParsed.get("email"), bodyParsed.get("senha"));
        if (u == null) {
            //TODO: tratar erro
            return "redirect:/index";
        } 

        //TODO: criar a home!
        return "redirect:/home";
    }
    
}
