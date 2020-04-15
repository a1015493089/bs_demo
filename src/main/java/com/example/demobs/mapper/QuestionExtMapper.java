package com.example.demobs.mapper;

import com.example.demobs.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);

}