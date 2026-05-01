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

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(token + " é o seu código de recuperação");

            String html = """
                <div style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; max-width: 400px; margin: 0 auto; padding: 20px; border: 1px solid #e5e7eb; border-radius: 12px;">
                    <h2 style="color: #1f2937; text-align: center;">Recuperação de Acesso</h2>
                    <p style="color: #4b5563; font-size: 16px; text-align: center;">
                        Use o código abaixo para redefinir sua senha. Ele é válido por 15 minutos.
                    </p>
                    
                    <div style="background-color: #f3f4f6; padding: 20px; border-radius: 8px; text-align: center; margin: 25px 0;">
                        <span style="font-size: 32px; font-weight: bold; letter-spacing: 8px; color: #2563eb;">
                            %s
                        </span>
                    </div>
                    
                    <p style="color: #9ca3af; font-size: 12px; text-align: center;">
                        Se você não solicitou este código, ignore este e-mail por segurança.
                    </p>
                    <hr style="border: 0; border-top: 1px solid #f3f4f6; margin: 20px 0;">
                    <p style="text-align: center; font-weight: bold; color: #1f2937;">Barbearia System</p>
                </div>
            """.formatted(token);

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            System.out.println("Email de recuperação enviado com código para: " + to);

        } catch (Exception e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
            throw new RuntimeException("Falha no envio do e-mail de recuperação", e);
        }
    }
}