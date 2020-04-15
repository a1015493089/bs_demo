package com.example.demobs.controller;

import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        //增加阅读数 id不存在会抛出异常 进入error.html
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
