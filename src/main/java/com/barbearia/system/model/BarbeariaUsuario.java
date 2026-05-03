package com.barbearia.system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BarbeariaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "valor", nullable = false)
    private int valor;

    @Column(name = "data_agendamento", nullable = false)
    private String dataAgendamento;


}
