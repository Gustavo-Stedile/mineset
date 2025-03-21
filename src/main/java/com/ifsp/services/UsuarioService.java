package com.ifsp.services;



import com.ifsp.teste.models.Usuario;

public interface UsuarioService {
    public Usuario autenticar(String email, String senha);
    public void criar(Usuario u);
}
