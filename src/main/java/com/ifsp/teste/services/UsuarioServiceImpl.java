package com.ifsp.teste.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsp.teste.models.BD;
import com.ifsp.teste.models.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    public Usuario autenticar(String nome, String senha) throws Exception {
        BD bd = BD.getInstance();
        bd.resgatar("Usuario: u_" + nome);
        
        File file = new File("usuario/usuario.properties");
        if (!file.exists()) return null;
        
        Properties props = new Properties();
        FileInputStream in = null;
        props.load(in = new FileInputStream(file));
        
        if (!props.getProperty("senha").equals(senha)) return null;
        Usuario u = new Usuario(props.getProperty("usuario"), props.getProperty("senha"));
        System.err.println(props.getProperty("usuario"));
        
        in.close();
        file.delete();
        return u;
    }

    public void criar(Usuario u) throws IOException {
        BD bd = BD.getInstance();

        Properties props = new Properties();

        props.setProperty("usuario", u.getNome());
        props.setProperty("senha", u.getSenha());

        try (FileOutputStream out = new FileOutputStream("usuario.properties")) {
            props.store(out, "Arquivo de configuracao");
            System.out.println("criado com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("usuario.properties");
        bd.enviar("Usuario: u_" + u.getNome(), file);
        file.delete();

    }
    
}
