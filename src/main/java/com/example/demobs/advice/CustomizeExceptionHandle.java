package com.example.demobs.advice;

import com.example.demobs.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model){
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            System.out.println(e.toString()); //控制台输出错误信息
            model.addAttribute("message","服务器错误，请稍微再试。");

        }
        return  new ModelAndView("error");

    }

}
