package com.example.demobs.controller;

import com.example.demobs.dto.CommentCreatDTO;
import com.example.demobs.dto.ResultDTO;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.model.Comment;
import com.example.demobs.model.User;
import com.example.demobs.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreatDTO commentCreatDTO,
                       HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
           return ResultDTO.errorof(CustomizeExceptionCode.NOT_LOGIN);
        }
       if(commentCreatDTO==null|| StringUtils.isBlank(commentCreatDTO.getContent())){
            return ResultDTO.errorof(CustomizeExceptionCode.COMMENT_IS_EMPTY);
        }


        Comment comment = new Comment();
        comment.setParentId(commentCreatDTO.getParentID());
        comment.setContent(commentCreatDTO.getContent());
        comment.setParentType(commentCreatDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okof();
    }

}
