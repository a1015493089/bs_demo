package com.example.demobs.service;

import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
        throw  new CustomizeException(CustomizeExceptionCode.TARGET_PARAM_NOT_FOUND);
        }
    }
}
