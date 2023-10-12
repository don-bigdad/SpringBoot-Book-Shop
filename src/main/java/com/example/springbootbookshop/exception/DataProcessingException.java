package com.example.springbootbookshop.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message,Throwable cause) {
        super(message,cause);
    }
}
