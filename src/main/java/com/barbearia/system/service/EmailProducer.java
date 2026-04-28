package com.barbearia.system.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.system.DTO.EmailMessage;
import com.barbearia.system.RabbitMQ.RabbitConfig;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String email, String token) {

        EmailMessage message = new EmailMessage();
        message.setEmail(email);
        message.setToken(token);

        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE,
            RabbitConfig.ROUTING_KEY,
            message
        );
        System.out.println("Mensagem enviada para fila: " + email + " com token: " + token);
    }

}
