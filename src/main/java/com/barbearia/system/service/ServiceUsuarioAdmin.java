package com.barbearia.system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.barbearia.system.exception.MyRuntimeException;
import com.barbearia.system.model.UsuarioAdmin;
import com.barbearia.system.repository.RepositoryUsuarioAdmin;
import com.barbearia.system.security.JwtService;

@Service
public class ServiceUsuarioAdmin {

    @Autowired
    private RepositoryUsuarioAdmin usuarioAdminRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Verifica se existe um usuário admin, caso contrário, cria um padrão. Retorna
     * a lista de usuários admin.
     * 
     * @return
     */
    public List<UsuarioAdmin> getUsuarioAdmin() {

        if (usuarioAdminRepository.count() == 0) {

            UsuarioAdmin admin = new UsuarioAdmin();

            admin.setNome("admin");
            admin.setEmail("admin@admin.com");
            // 🔐 hash da senha
            // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            admin.setSenha(encoder.encode("admin123"));

            usuarioAdminRepository.save(admin);
        }
        return usuarioAdminRepository.findAll();
    }

    /**
     * Salva um usuário admin no banco de dados. Retorna o usuário salvo.
     * 
     * @param usuarioAdmin
     * @return
     */
    public UsuarioAdmin saveUsuarioAdmin(UsuarioAdmin usuarioAdmin) {

        // 🔐 hash da senha
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UsuarioAdmin existing = usuarioAdminRepository.findByEmail(usuarioAdmin.getEmail()).orElse(null);

        if (existing != null) {
            // Se o usuário já existe, manter a senha atual
            usuarioAdmin.setSenha(existing.getSenha());
        } else {
            // Novo usuário, precisa de senha
            String senhaHash = encoder.encode(usuarioAdmin.getSenha());
            usuarioAdmin.setSenha(senhaHash);
        }

        return usuarioAdminRepository.save(usuarioAdmin);
    }

    /**
     * Busca um usuário admin pelo ID. Retorna o usuário encontrado ou null se não
     * existir.
     * 
     * @param id
     * @return
     */
    public UsuarioAdmin getUsuarioAdminById(int id) {
        return usuarioAdminRepository.findById(id).orElse(null);
    }

    /**
     * Exclui um usuário admin do banco de dados.
     * 
     * @param id
     */
    public void deleteUsuarioAdmin(int id) {
        usuarioAdminRepository.deleteById(id);
    }

    /**
     * Realiza o login do usuário admin, verificando email e senha. Retorna o
     * usuário encontrado ou null se não existir.
     * 
     * @param email
     * @param senha
     * @return
     */
    public String loginService(String email, String senha) {
        
        System.out.println("Senha fornecida: " + senha + "\n");
        
        UsuarioAdmin usuarioAdmin = usuarioAdminRepository.findByEmail(email).orElse(null);
    
        if (usuarioAdmin == null) {
            System.out.println("Usuario não Cadastrado");
            return null;
        }

        System.out.println("Senha hash: " + usuarioAdmin.getSenha());
        // 🔐 comparar senhas
        boolean asSenhasSaoIguais = encoder.matches(senha, usuarioAdmin.getSenha());

        if (!asSenhasSaoIguais) {
            System.out.println("Senha incorreta");
            return null;
        }

        return jwtService.generateToken(usuarioAdmin.getEmail());
    }

    /**
     * Busca um usuário admin pelo email. Retorna o usuário encontrado ou null se
     * não existir.
     * 
     * @param email
     * @return
     */
    public UsuarioAdmin findByEmailService(String email) {
        Optional<UsuarioAdmin> usuario = usuarioAdminRepository.findByEmail(email);
        if (usuario.isEmpty()) {
            return null;
        }
        return usuario.get();
    }

    /**
     * Busca um usuário admin pelo email. Retorna o usuário encontrado ou null se
     * não existir.
     * 
     * @param email
     * @return
     */
    public void recoverPassword(String email) {

        UsuarioAdmin usuarioAdmin = usuarioAdminRepository.findByEmail(email).orElse(null);

        // 🔐 IMPORTANTE: não revelar se usuário existe
        if (usuarioAdmin == null) {
            return;
        }
        // String token = jwtService.generateToken(email);
        String token = String.format("%06d", (int) (Math.random() * 1000000));

        usuarioAdmin.setResetToken(token);
        usuarioAdmin.setResetTokenExpires(LocalDateTime.now().plusMinutes(15));

        usuarioAdminRepository.save(usuarioAdmin);
        System.out.println("\nDados do usuário para recuperação de senha:");
        System.out.println("\n======\n");
        System.out.println("Token de recuperação gerado para " + email + ": " + token);
        System.out.println("\n======\n");
        System.out.println("Token expira em: " + LocalDateTime.now().plusMinutes(15));
        System.out.println("\n======\n");

        // ENVIA PARA FILA (não envia email direto)
        try {
            emailProducer.send(usuarioAdmin.getEmail(), token);
            System.out.println("\nMensagem enviada para fila!\n");
        } catch (Exception e) {
            System.out.println("\nERRO AO ENVIAR PARA RABBIT: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
   
    }

    public void resetPassword(String token, String novaSenha) {

        // 🔐 IMPORTANTE: não revelar se token é válido
        UsuarioAdmin usuarioAdmin = usuarioAdminRepository.findByResetToken(token)
                .orElseThrow(() -> new MyRuntimeException("Token inválido"));

        // ⏰ verificar expiração
        if (usuarioAdmin.getResetTokenExpires().isBefore(LocalDateTime.now())) {
            throw new MyRuntimeException("Token expirado - solicite um novo token");
        }

        // 🔐 hash da senha
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaHash = encoder.encode(novaSenha);

        usuarioAdmin.setSenha(senhaHash);

        // ❌ invalidar token
        usuarioAdmin.setResetToken(null);
        usuarioAdmin.setResetTokenExpires(null);

        usuarioAdminRepository.save(usuarioAdmin);
    }

}
