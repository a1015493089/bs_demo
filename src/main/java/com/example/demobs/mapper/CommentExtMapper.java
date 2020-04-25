package com.example.demobs.mapper;

import com.example.demobs.model.Comment;
import com.example.demobs.model.CommentExample;
import com.example.demobs.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}