package com.barbearia.system.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoveryEmail(String to, String token) {

        try {
            String link = "http://localhost:8080/application/json/reset-password?token=" + token;

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Recuperação de senha");

            String html = """
                <div style="font-family: Arial;">
                    <h2>Recuperação de senha</h2>
                    <p>Você solicitou a redefinição de senha.</p>

                    <a href="%s"
                       style="display:inline-block;padding:10px 20px;
                              background:#2563eb;color:white;
                              text-decoration:none;border-radius:5px;">
                        Redefinir senha
                    </a>

                    <p>Esse link expira em 15 minutos.</p>
                </div>
            """.formatted(link);

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            System.out.println("Email enviado para: " + to);

        } catch (Exception e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}