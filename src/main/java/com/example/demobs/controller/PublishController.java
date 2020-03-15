package com.example.demobs.controller;

import com.example.demobs.mapper.QuestionMapper;
import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.Question;
import com.example.demobs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
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


        User user=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){              //登入态检验
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                     user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                }
            }
        }
        if(user==null) {
            model.addAttribute("error","用户未登入");
            return "publish";}
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreat(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreat());

        questionMapper.creat(question);
        return "redirect:/";

    }
}
