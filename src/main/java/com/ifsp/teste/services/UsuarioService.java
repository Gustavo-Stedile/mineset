package com.ifsp.teste.services;

import com.ifsp.teste.models.Usuario;

public interface UsuarioService {
    public void criar(Usuario u);
    public Usuario autenticar(String email, String senha);
}
