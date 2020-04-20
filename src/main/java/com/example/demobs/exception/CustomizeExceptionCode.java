package com.example.demobs.exception;

public enum CustomizeExceptionCode implements  ICustomizeExceptionCode {
    QUESTION_NOT_FOUND(2001,"该问题不存在。"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者回复。"),
    NOT_LOGIN(2003,"用户未登录,请登入后重试。"),
    SYS_ERROR(2004,"服务器熟了。"),
    TYPE_PARAM_NOT_FOUND(2005,"回复对象类型不明。"),
    COMMENT_NOT_FOUND(2006,"执行回复的对象回复不存在。");

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
