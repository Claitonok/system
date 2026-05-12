package com.barbearia.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.barbearia.system.exception.MyRuntimeException;
import com.barbearia.system.model.UsuarioApiSecurity;
import com.barbearia.system.repository.RepositoryUsuarioApiSecurity;

@Service
public class UsuarioApiSecurityService {

    @Autowired
    private RepositoryUsuarioApiSecurity repository;

    // O segredo para a autenticação funcionar: BCrypt
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 
     * @param usuario
     * @return
     */
    public UsuarioApiSecurity criarUsuario(UsuarioApiSecurity usuario) {

        // Criptografa a senha antes de enviar para o banco
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);

        // Garante que a role tenha o padrão que o Spring espera (opcional, mas
        // recomendado)
        if (!usuario.getRoles().startsWith("ROLE_")) {
            usuario.setRoles(usuario.getRoles().toUpperCase());
        }
        // 2. Validação de Roles (Exemplo de blindagem)
        if (!usuario.getRoles().equals("ADMIN") && !usuario.getRoles().equals("USER")) {
            throw new MyRuntimeException("Role inválida!");
        }

        return repository.save(usuario);
    }
}
