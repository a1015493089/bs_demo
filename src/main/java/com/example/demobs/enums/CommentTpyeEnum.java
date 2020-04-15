package com.example.demobs.enums;

public enum  CommentTpyeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer parentType) {
        for (CommentTpyeEnum commentTpyeEnum : CommentTpyeEnum.values()) {
            if(commentTpyeEnum.getType() == parentType)
            return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTpyeEnum(Integer type) {
        this.type = type;
    }
}
