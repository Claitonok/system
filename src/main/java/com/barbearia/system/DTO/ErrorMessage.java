package com.barbearia.system.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorMessage {

    int status;
    String message;
    LocalDateTime timestamp;

    public ErrorMessage(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
