package com.example.demobs.dto;

import com.example.demobs.exception.CustomizeExceptionCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static  ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return  resultDTO;
    }

    public static ResultDTO errorof(CustomizeExceptionCode notLogin) {
        return  errorof(notLogin.getCode(),notLogin.getMessage());
    }
    public static ResultDTO okof() {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return  resultDTO;
    }
}
