package com.example.flowboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMoveCardException extends RuntimeException {
    private final String code;

    public InvalidMoveCardException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
