package com.example.demobs.enums;

public enum  CommentTpyeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTpyeEnum(Integer type) {
        this.type = type;
    }
}
