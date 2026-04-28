package com.barbearia.system.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.system.DTO.EmailMessage;
import com.barbearia.system.RabbitMQ.RabbitConfig;

@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void receive(EmailMessage msg) {

        System.out.println("Mensagem recebida da fila");

        emailService.sendRecoveryEmail(
            msg.getEmail(),
            msg.getToken()
        );
    }
}