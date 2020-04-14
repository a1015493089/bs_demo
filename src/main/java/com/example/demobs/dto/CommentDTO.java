package com.example.demobs.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private long parentID;
    private String content;
    private int type;
}
