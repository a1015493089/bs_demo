package com.example.demobs.service;

import com.example.demobs.enums.CommentTpyeEnum;
import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.mapper.CommentMapper;
import com.example.demobs.mapper.QuestionExtMapper;
import com.example.demobs.mapper.QuestionMapper;
import com.example.demobs.model.Comment;
import com.example.demobs.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            //判断父类对象ID是否存在或者为0
        throw  new CustomizeException(CustomizeExceptionCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getParentType()==null|| !CommentTpyeEnum.isExist(comment.getParentType())){
            //判断父类类型是否存在
            throw  new CustomizeException(CustomizeExceptionCode.TYPE_PARAM_NOT_FOUND);
        }

        if(comment.getParentType()==CommentTpyeEnum.COMMENT.getType()){
            //操作对象为评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw  new CustomizeException(CustomizeExceptionCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //操作对象为问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion==null){
                throw  new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
        }
    }
}
