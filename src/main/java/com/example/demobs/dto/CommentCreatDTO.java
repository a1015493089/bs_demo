package com.example.demobs.dto;

import lombok.Data;

@Data
public class CommentCreatDTO {
    private long parentID;
    private String content;
    private int type;
}
