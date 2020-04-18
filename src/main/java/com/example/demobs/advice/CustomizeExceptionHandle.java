package com.example.demobs.advice;

import com.alibaba.fastjson.JSON;
import com.example.demobs.dto.ResultDTO;
import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request,
                        HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //非页面跳转
            ResultDTO resultDTO;
            if(e instanceof CustomizeException){
                resultDTO=ResultDTO.errorof((CustomizeException)e);
            }else {
                resultDTO=ResultDTO.errorof(CustomizeExceptionCode.SYS_ERROR);
                System.out.println(e.toString());
            }
            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("utf-8");

            try {
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        else {
            //页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeExceptionCode.SYS_ERROR.getMessage());
                System.out.println(e.toString());

            }
        return  new ModelAndView("error");

        }
    }

}
