package com.example.demo.exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(Throwable e) {
        super(e);
    }
}
