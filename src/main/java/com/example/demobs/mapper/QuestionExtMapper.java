package com.example.demobs.mapper;

import com.example.demobs.dto.QuestionQueryDTO;
import com.example.demobs.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> countSelectSearch(QuestionQueryDTO questionQueryDTO);
}
