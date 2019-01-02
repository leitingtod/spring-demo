package com.example.demo.exceptions;

public class BadFormatException extends Exception {
    public BadFormatException(String s) {
        super(s);
    }

    public BadFormatException(Throwable t) {
        super(t);
    }
}
