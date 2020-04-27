package com.example.demobs.controller;

import com.example.demobs.dto.CommentDTO;
import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.enums.CommentTpyeEnum;
import com.example.demobs.service.CommentService;
import com.example.demobs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);
        //增加阅读数 id不存在会抛出异常 进入error.html
        questionService.incView(id);
        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTpyeEnum.QUESTION);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
