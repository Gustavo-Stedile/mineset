package com.ifsp.teste.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsp.teste.models.Usuario;
import com.ifsp.teste.repositories.UserRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UserRepository userRepo;

    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> u = userRepo.findByEmail(email);
        if (u.isEmpty()) return null;

        if (!u.get().getSenha().equals(senha)) return null;
        return u.get();
    }

    public void criar(Usuario u) {
        userRepo.save(u);
    }
    
}
