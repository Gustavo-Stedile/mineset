package com.ifsp.teste.services;

import java.io.IOException;

import com.ifsp.teste.models.Usuario;

public interface UsuarioService {
    public void criar(Usuario u) throws IOException;
    public Usuario autenticar(String email, String senha) throws Exception;
}
