package com.barbearia.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbearia.system.model.UsuarioAdmin;

public interface RepositoryUsuarioAdmin extends JpaRepository<UsuarioAdmin, Integer> {

    Optional<UsuarioAdmin> findByEmail(String email);
    
    Optional<UsuarioAdmin> findByEmailAndSenha(String email, String senha);

    Optional<UsuarioAdmin> findByResetToken(String resetToken);

}
