package com.ifsp.teste.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.teste.exceptions.UnauthorizedAccessException;
import com.ifsp.teste.models.BD;
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

    @PostMapping("/upload")
    public String upload(
        HttpSession session, 
        
        @RequestParam String classe,
        @RequestParam(name="file") MultipartFile file,
        Model model
    ) {
        Usuario u = (Usuario) session.getAttribute("usuarioAutenticado");

        try {
            String tmpDir = System.getProperty("java.io.tmpdir");
            // Create a file with the exact name "Strategy.class" in that directory
            File convFile = new File(tmpDir, "Strategy.class");
            // Cria um arquivo temporário, com prefixo e sufixo baseados no original
            file.transferTo(convFile);
            // Agora convFile é um java.io.File local com o conteúdo do upload
            model.addAttribute("arquivo", convFile);

            BD.getInstance().enviar("Classe: c"+u.getNome()+"_"+classe, convFile);

        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao salvar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    

        if (u == null) {
            throw new UnauthorizedAccessException();
        }
        return "upload";
    }
}