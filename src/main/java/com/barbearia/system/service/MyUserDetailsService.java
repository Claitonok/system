package com.barbearia.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.barbearia.system.exception.MyRuntimeException;
import com.barbearia.system.model.UsuarioApiSecurity;
import com.barbearia.system.repository.RepositoryUsuarioApiSecurity;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositoryUsuarioApiSecurity repositoryUsuariopiSecurity;

    @Override
    public UserDetails loadUserByUsername(String username) throws MyRuntimeException {

            UsuarioApiSecurity usuarioApiSecurity = repositoryUsuariopiSecurity.findByUsername(username);
            
            if (usuarioApiSecurity == null) {
                throw new MyRuntimeException("Usuário não encontrado: " + username);
            }

            System.out.println("\n=== Detalhes do usuário para autenticação ===\n");
            System.out.println("Usuário encontrado: " + usuarioApiSecurity.getUsername());
            System.out.println("Senha: " + usuarioApiSecurity.getPassword());
            System.out.println("Roles do usuário: " + usuarioApiSecurity.getRoles());

           // Constrói um UserDetails usando os dados do usuário encontrado
            return User.builder()
                    .username(usuarioApiSecurity.getUsername())
                    .password(usuarioApiSecurity.getPassword())
                    //.password(new BCryptPasswordEncoder().encode("0147")) // Codifica a senha "0147" para comparação
                    .roles(usuarioApiSecurity.getRoles()) // Adiciona o prefixo ROLE_ aqui
                    .build();
    }
}
