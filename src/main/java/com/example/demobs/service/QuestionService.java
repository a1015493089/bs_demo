package com.example.demobs.service;

import com.example.demobs.dto.PaginationDTO;
import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.mapper.QuestionMapper;
import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.Question;
import com.example.demobs.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);//输入最大行目 当前页数 每页容量 然后设置对应的参数值
        if(page<1)page=1;
        else if(page>paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();

        Integer offset=size*(page-1);       //数据库查询下标
        List<Question> questions = questionMapper.list(offset,size);//数据库查询语句执行
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //question到questionDTO的转换
        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size); //输入最大行目 当前页数 每页容量 然后设置对应的参数值
        if(page<1)page=1;
        else if(page>paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();
        Integer offset=size*(page-1);       //数据库查询下标
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);//数据库查询语句执行
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //question到questionDTO的转换
        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
    //通过问题的ID码查找问题详情页面
    public QuestionDTO getQuestionById(Integer id) {
        Question question=questionMapper.getQuestionById(id);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void creatOrUpdate(Question question) {
        if(question.getId()==null){ //没有问题ID 新建
            question.setGmtCreat(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreat());
            questionMapper.creat(question);
        }else {//已有问题ID 修改
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
