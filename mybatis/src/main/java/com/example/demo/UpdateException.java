package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "update account failed")
public class UpdateException extends RuntimeException {
    private String message;
    UpdateException (String message) {
        this.message = message;
    }
}
