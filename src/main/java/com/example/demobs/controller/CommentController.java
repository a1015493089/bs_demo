package com.example.demobs.controller;

import com.example.demobs.dto.CommentCreatDTO;
import com.example.demobs.dto.CommentDTO;
import com.example.demobs.dto.ResultDTO;
import com.example.demobs.enums.CommentTpyeEnum;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.model.Comment;
import com.example.demobs.model.User;
import com.example.demobs.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO< List<CommentDTO>> comments(@PathVariable(name="id")Long id){
        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTpyeEnum.COMMENT);
        return ResultDTO.okof(comments);
    }

}
