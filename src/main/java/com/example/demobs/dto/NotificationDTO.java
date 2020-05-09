package com.example.demobs.dto;


import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreat;
    private Integer status;
    private Long notifier;
    private String outerTitle;
    private String typeName;
    private String notifierName;
    private Long outerid;
    private Integer type;
}
