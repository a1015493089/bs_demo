package com.example.demobs.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long  gmtCreat;
    private Long gmtModified;
    private String avatarUrl;

}

