package com.example.demobs.dto;

import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
    public static  ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return  resultDTO;
    }

    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage());
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
    public static <T> ResultDTO okof(T t) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        resultDTO.setData(t);
        return  resultDTO;
    }
}
