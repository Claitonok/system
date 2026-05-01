package com.barbearia.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.system.DTO.ForgotPasswordRequestDTO;
import com.barbearia.system.model.BarbeariaUsuario;
import com.barbearia.system.model.UsuarioAdmin;
import com.barbearia.system.service.ServiceUsuarioAdmin;
import com.barbearia.system.service.ServicebarbeariaUsuario;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/application/json")
public class ControllerSystem {

    @Autowired
    private ServiceUsuarioAdmin authService;

    @Autowired
    private ServicebarbeariaUsuario servicebarbeariaUsuario;

    @GetMapping("/usuarios-admin")
    public ResponseEntity<List<UsuarioAdmin>> usuarioAdmin() {

        List<UsuarioAdmin> usuarios = authService.getUsuarioAdmin(); // Garante que o usuário admin padrão exista

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<UsuarioAdmin> saveUsuarioAdmin(@RequestBody UsuarioAdmin usuarioAdmin) {

        UsuarioAdmin savedUsuarioAdmin = authService.saveUsuarioAdmin(usuarioAdmin);

        return new ResponseEntity<>(savedUsuarioAdmin, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {

        try {
                UsuarioAdmin usuarioAdmin = authService.getUsuarioAdminById(id);
        if (usuarioAdmin == null) {
             return ResponseEntity.status(404).body("Usuário não encontrado");  
        }

            authService.deleteUsuarioAdmin(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> login(@RequestBody UsuarioAdmin usuarioAdmin) {

        String token = authService.loginService(usuarioAdmin.getEmail(), usuarioAdmin.getSenha());

        if (token == null) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuarioAdmin", usuarioAdmin);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {

        UsuarioAdmin usuarioAdmin = authService.findByEmailService(request.getEmail());

        if (usuarioAdmin != null) {
            authService.recoverPassword(request.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(404).body("Email não encontrado");
    }

    @PostMapping("/admin/reset-password")
    public ResponseEntity<?> reset(@RequestBody UsuarioAdmin usuarioAdmin) {

        authService.resetPassword(
                usuarioAdmin.getResetToken(),
                usuarioAdmin.getSenha());

        return new ResponseEntity<>(HttpStatus.OK);
    }



    // Outros endpoints relacionados ao Agendamento da barbearia podem ser adicionados aqui

    @GetMapping("/barbearia-agendamentos")
    public ResponseEntity<List<BarbeariaUsuario>> getBarbeariaUsuario() {

        List<BarbeariaUsuario> barbeariaUsuarios = servicebarbeariaUsuario.getAllBarbeariaUsuarios();

        return new ResponseEntity<>(barbeariaUsuarios, HttpStatus.OK);
    }

    @PostMapping("/barbearia-cadastro")
    public ResponseEntity<BarbeariaUsuario> createBarbeariaUsuario(@RequestBody BarbeariaUsuario barbeariaUsuario) {

        BarbeariaUsuario savedBarbeariaUsuario = servicebarbeariaUsuario.saveBarbeariaUsuario(barbeariaUsuario);

        return new ResponseEntity<BarbeariaUsuario>(savedBarbeariaUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/barbearia-atualizar/{id}")
    public ResponseEntity<?> updateBarbeariaUsuario(@PathVariable int id, @RequestBody BarbeariaUsuario barbeariaUsuario) {

        BarbeariaUsuario existingBarbeariaUsuario = servicebarbeariaUsuario.getBarbeariaUsuarioById(id);

        if (existingBarbeariaUsuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
        BarbeariaUsuario updatedBarbeariaUsuario = servicebarbeariaUsuario.atualizarBarbeariaUsuarioById(id, barbeariaUsuario);
        return new ResponseEntity<>(updatedBarbeariaUsuario, HttpStatus.OK);
    }

    @GetMapping("/barbearia-usuario/{id}")
    public ResponseEntity<?> barbeariaUsuarioById(@PathVariable int id) {

        BarbeariaUsuario barbeariaUsuario = servicebarbeariaUsuario.getBarbeariaUsuarioById(id);

        if (barbeariaUsuario == null) {
             return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        return new ResponseEntity<>(barbeariaUsuario, HttpStatus.OK);
    }

    @DeleteMapping("/barbearia-deletar/{id}")
    public ResponseEntity<?> deleteBarbeariaUsuario(@PathVariable int id) {

        try {
            BarbeariaUsuario barbeariaUsuario = servicebarbeariaUsuario.getBarbeariaUsuarioById(id);

        if (barbeariaUsuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

            servicebarbeariaUsuario.deleteBarbeariaUsuario(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

    }


}
