package com.example.demobs.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeExceptionCode iCustomizeExceptionCode) {
        this.message = iCustomizeExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
