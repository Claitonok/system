package com.barbearia.system.DTO;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailMessage implements Serializable{

    private String email;
    private String token;

}
