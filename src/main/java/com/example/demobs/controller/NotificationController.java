package com.example.demobs.controller;

import com.example.demobs.dto.NotificationDTO;
import com.example.demobs.enums.NotificationTypeEnum;
import com.example.demobs.model.User;
import com.example.demobs.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id")Long id,
                           HttpServletRequest request){
        //登入态检验
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
       NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||
           NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }

}

