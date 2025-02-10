package com.example.springblog.springblog.exeption;

public class EmailAlreadyExistingException extends RuntimeException {
    public EmailAlreadyExistingException(String message) {
        super(message);
    }
}
