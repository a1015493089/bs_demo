package com.example.demobs.service;

import com.example.demobs.dto.CommentDTO;
import com.example.demobs.enums.CommentTpyeEnum;
import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.mapper.*;
import com.example.demobs.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    UserMapper userMapper;
    @Transactional
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
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            //显示二级回复总数
            Comment dbcomment=new Comment();
            dbComment.setId(comment.getParentId());
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);
        }else {
            //操作对象为问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion==null){
                throw  new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            //显示回复总数
            commentMapper.insert(comment);
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTpyeEnum commentTpyeEnum) {
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andParentTypeEqualTo(commentTpyeEnum.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //拿取每个评论的用户 无序可重复
        Set<Long> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userids = new ArrayList<>();
        userids.addAll(collect);
        //为之后降低时间复杂度 所以需要转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userids);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //降低时间复杂度后的数据类型转换
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
