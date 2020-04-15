package com.example.demobs.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeExceptionCode iCustomizeExceptionCode) {
        this.message = iCustomizeExceptionCode.getMessage();
        this.code=iCustomizeExceptionCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
