package com.barbearia.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.system.model.BarbeariaUsuario;
import com.barbearia.system.repository.RepositoryBarbeariaUsuario;

@Service
public class ServicebarbeariaUsuario {

    @Autowired
    private RepositoryBarbeariaUsuario repositoryBarbeariaUsuario;

    /**
     * Retorna a lista de todos os usuários da barbearia.
     * @return
     */
    public List<BarbeariaUsuario> getAllBarbeariaUsuarios() {
        return repositoryBarbeariaUsuario.findAll();
    }

    /**
     * Salva um usuário da barbearia no banco de dados. Retorna o usuário salvo.
     * @param barbeariaUsuario
     * @return
     */
    public BarbeariaUsuario saveBarbeariaUsuario(BarbeariaUsuario barbeariaUsuario) {
        return repositoryBarbeariaUsuario.save(barbeariaUsuario);
    }

    /**
     * Atualiza um usuário da barbearia pelo seu ID. Retorna null se o usuário não for encontrado.
     * @param id
     * @param barbeariaUsuario
     * @return
     */
    public BarbeariaUsuario atualizarBarbeariaUsuarioById(int id, BarbeariaUsuario barbeariaUsuario) {
         
      Optional<BarbeariaUsuario> barbeariaUsuarioOptional = repositoryBarbeariaUsuario.findById(id);

        if (barbeariaUsuarioOptional.isPresent()) {
            barbeariaUsuario.setId(id);
            return repositoryBarbeariaUsuario.save(barbeariaUsuario);
        } else {
            return null;
        }
    }

    /**
     * Retorna um usuário da barbearia pelo seu ID. Retorna um Optional vazio se o usuário não for encontrado.
     * @param id
     * @return
     */
    public BarbeariaUsuario getBarbeariaUsuarioById(int id) {

        Optional<BarbeariaUsuario> barbeariaUsuarioOptional = repositoryBarbeariaUsuario.findById(id);

        if (!barbeariaUsuarioOptional.isPresent()) {
            return null;
        }
        return barbeariaUsuarioOptional.get();
    }

    /**
     * Exclui um usuário da barbearia do banco de dados.
     * @param id
     */
    public void deleteBarbeariaUsuario(int id) {
        repositoryBarbeariaUsuario.deleteById(id);
    }


}
