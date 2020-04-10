package com.example.demobs.exception;

public enum CustomizeExceptionCode implements  ICustomizeExceptionCode {
    QUESTION_NOT_FOUND("该问题不存在，点击按钮返回主页。");
    private  String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeExceptionCode(String message) {
        this.message = message;
    }
}
