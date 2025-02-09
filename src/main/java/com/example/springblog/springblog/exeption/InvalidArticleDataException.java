package com.example.springblog.springblog.exeption;

public class InvalidArticleDataException extends RuntimeException {
    public InvalidArticleDataException(String message) {
        super(message);
    }
}
