package com.example.demobs.controller;

import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.model.Question;
import com.example.demobs.model.User;
import com.example.demobs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("title",question.getTitle());  //为了编辑
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish") //地址栏访问
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")   //表单访问
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title",title);  //为了回显
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description.equals("")){
            model.addAttribute("error","补充内容不能为空");
            return "publish";
        }
        if(tag==null||tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //登入态检验
        User user=(User)request.getSession().getAttribute("user");
        if(user==null) {
            model.addAttribute("error","用户未登入");
            return "publish";}
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());

        question.setId(id);
        questionService.creatOrUpdate(question);
        return "redirect:/";

    }
}
