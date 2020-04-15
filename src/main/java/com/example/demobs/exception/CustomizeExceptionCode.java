package com.example.demobs.exception;

public enum CustomizeExceptionCode implements  ICustomizeExceptionCode {
    QUESTION_NOT_FOUND(2001,"该问题不存在，点击按钮返回主页。"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者回复。"),
    NOT_LOGIN(2003,"用户未登录");

    private  String message;
    private Integer code;

    CustomizeExceptionCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
