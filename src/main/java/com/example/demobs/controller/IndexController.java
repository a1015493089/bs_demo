package com.example.demobs.controller;

import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.mapper.QuestionMapper;
import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.Question;
import com.example.demobs.model.User;
import com.example.demobs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public  String index(HttpServletRequest request,
                         Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){              //登入态检验
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                }
            }
        }
        List<QuestionDTO> questionsList=questionService.list();
        model.addAttribute("questions",questionsList);
        return "index";
    }
}
