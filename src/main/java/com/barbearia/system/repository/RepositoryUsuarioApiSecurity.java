package com.barbearia.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbearia.system.model.UsuarioApiSecurity;

public interface RepositoryUsuarioApiSecurity extends JpaRepository<UsuarioApiSecurity, Integer> {

    UsuarioApiSecurity findByUsername(String username);

}
