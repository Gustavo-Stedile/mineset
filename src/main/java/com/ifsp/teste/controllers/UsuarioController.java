package com.ifsp.teste.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.teste.exceptions.UnauthorizedAccessException;
import com.ifsp.teste.models.Usuario;
import com.ifsp.teste.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {   

    @Autowired
    public UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public String signup(
        @RequestParam String nome, 
        @RequestParam String email,
        @RequestParam String senha
        
    ) throws IOException {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setSenha(senha);

        usuarioService.criar(u);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(
        Model model,
        @RequestParam String nome,
        @RequestParam String senha,
        HttpSession session
    ) throws Exception {
        Usuario u = usuarioService.autenticar(nome, senha);
        
        if (u == null) {
            model.addAttribute("mensagem", "email ou senha incorretos");
            return "/login";
        }

        // sessão p/ usuario acessar outras paginas
        session.setAttribute("usuarioAutenticado", u); 
        return "redirect:/home";
    }

    @GetMapping("/documentacao")
    public String documentacao(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioAutenticado");

        if (u == null) {
            throw new UnauthorizedAccessException();
        }
        return "documentacao";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioAutenticado");

        if (u == null) {
            throw new UnauthorizedAccessException();        }
        return "configuracoes";
    }

    @GetMapping("/relatorios")
    public String relatorios(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioAutenticado");

        if (u == null) {
            throw new UnauthorizedAccessException();
        }
        return "relatorios";
    }

    @GetMapping("/upload")
    public String upload(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioAutenticado");

        if (u == null) {
            throw new UnauthorizedAccessException();
        }
        return "upload";
    }
}